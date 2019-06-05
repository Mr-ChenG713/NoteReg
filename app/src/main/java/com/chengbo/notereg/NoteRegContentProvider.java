package com.chengbo.notereg;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NoteRegContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.chengbo.notereg";
    public static final String TIPO = "tipo";
    public static final String SERVICO = "servico";
    public static final String MOVIMENTO = "movimento";

    private static final Uri ENDERECO_BASE = Uri.parse("content://" + AUTHORITY);
    public static final Uri ENDERECO_TIPO = Uri.withAppendedPath(ENDERECO_BASE, TIPO);
    public static final Uri ENDERECO_SERVICO = Uri.withAppendedPath(ENDERECO_BASE, SERVICO);
    public static final Uri ENDERECO_MOVIMENTO = Uri.withAppendedPath(ENDERECO_BASE, MOVIMENTO);

    public static final int URI_TIPO = 200;
    public static final int URI_TIPO_UNICO = 201;
    public static final int URI_SERVICO = 300;
    public static final int URI_SERVICO_UNICO = 301;
    public static final int URI_MOVIMENTO = 400;
    public static final int URI_MOVIMENTO_UNICO = 401;

    public static final String UNICO_ITEM = "vnd.android.cursor.item/";
    public static final String MULTIPLOS_ITEMS = "vnd.android.cursor.dir/";

    private BdNoteRegOpenHelper bdNoteRegOpenHelper;

    private UriMatcher getUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, TIPO, URI_TIPO);
        uriMatcher.addURI(AUTHORITY, TIPO + "/#", URI_TIPO_UNICO);
        uriMatcher.addURI(AUTHORITY, SERVICO, URI_SERVICO);
        uriMatcher.addURI(AUTHORITY, SERVICO + "/#", URI_SERVICO_UNICO);
        uriMatcher.addURI(AUTHORITY, MOVIMENTO, URI_MOVIMENTO);
        uriMatcher.addURI(AUTHORITY, MOVIMENTO + "/#", URI_MOVIMENTO_UNICO);

        return uriMatcher;
    }

    /**
     * Implement this to initialize your content provider on startup.
     * This method is called for all registered content providers on the
     * application main thread at application launch time.  It must not perform
     * lengthy operations, or application startup will be delayed.
     *
     * <p>You should defer nontrivial initialization (such as opening,
     * upgrading, and scanning databases) until the content provider is used
     * (via {@link #query}, {@link #insert}, etc).  Deferred initialization
     * keeps application startup fast, avoids unnecessary work if the provider
     * turns out not to be needed, and stops database errors (such as a full
     * disk) from halting application launch.
     *
     * <p>If you use SQLite, {@link SQLiteOpenHelper}
     * is a helpful utility class that makes it easy to manage databases,
     * and will automatically defer opening until first use.  If you do use
     * SQLiteOpenHelper, make sure to avoid calling
     * {@link SQLiteOpenHelper#getReadableDatabase} or
     * {@link SQLiteOpenHelper#getWritableDatabase}
     * from this method.  (Instead, override
     * {@link SQLiteOpenHelper#onOpen} to initialize the
     * database when it is first opened.)
     *
     * @return true if the provider was successfully loaded, false otherwise
     */
    @Override
    public boolean onCreate() {

        bdNoteRegOpenHelper = new BdNoteRegOpenHelper(getContext());
        return true;
    }

    /**
     * Implement this to handle query requests from clients.
     *
     * <p>Apps targeting {@link Build.VERSION_CODES#O} or higher should override
     * {@link #query(Uri, String[], Bundle, CancellationSignal)} and provide a stub
     * implementation of this method.
     *
     * <p>This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     * <p>
     * Example client call:<p>
     * <pre>// Request a specific record.
     * Cursor managedCursor = managedQuery(
     * ContentUris.withAppendedId(Contacts.People.CONTENT_URI, 2),
     * projection,    // Which columns to return.
     * null,          // WHERE clause.
     * null,          // WHERE clause value substitution
     * People.NAME + " ASC");   // Sort order.</pre>
     * Example implementation:<p>
     * <pre>// SQLiteQueryBuilder is a helper class that creates the
     * // proper SQL syntax for us.
     * SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
     *
     * // Set the table we're querying.
     * qBuilder.setTables(DATABASE_TABLE_NAME);
     *
     * // If the query ends in a specific record number, we're
     * // being asked for a specific record, so set the
     * // WHERE clause in our query.
     * if((URI_MATCHER.match(uri)) == SPECIFIC_MESSAGE){
     * qBuilder.appendWhere("_id=" + uri.getPathLeafId());
     * }
     *
     * // Make the query.
     * Cursor c = qBuilder.query(mDb,
     * projection,
     * selection,
     * selectionArgs,
     * groupBy,
     * having,
     * sortOrder);
     * c.setNotificationUri(getContext().getContentResolver(), uri);
     * return c;</pre>
     *
     * @param uri           The URI to query. This will be the full URI sent by the client;
     *                      if the client is requesting a specific record, the URI will end in a record number
     *                      that the implementation should parse and add to a WHERE or HAVING clause, specifying
     *                      that _id value.
     * @param projection    The list of columns to put into the cursor. If
     *                      {@code null} all columns are included.
     * @param selection     A selection criteria to apply when filtering rows.
     *                      If {@code null} then all rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     *                      the values from selectionArgs, in order that they appear in the selection.
     *                      The values will be bound as Strings.
     * @param sortOrder     How the rows in the cursor should be sorted.
     *                      If {@code null} then the provider is free to define the sort order.
     * @return a Cursor or {@code null}.
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase bd = bdNoteRegOpenHelper.getReadableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_TIPO:
                return new BdTableTipo(bd).query(projection, selection, selectionArgs, null, null, sortOrder);
            case URI_TIPO_UNICO:
                return  new BdTableTipo(bd).query(projection, BdTableTipo._ID + "=?", new String[] { id }, null, null, null);
            case URI_SERVICO:
                return new BdTableServico(bd).query(projection, selection, selectionArgs, null, null, sortOrder);
            case URI_SERVICO_UNICO:
                return  new BdTableServico(bd).query(projection, BdTableServico._ID + "=?", new String[] { id }, null, null, null);
            case URI_MOVIMENTO:
                return new BdTableMovimento(bd).query(projection, selection, selectionArgs, null, null, sortOrder);
            case URI_MOVIMENTO_UNICO:
                return  new BdTableMovimento(bd).query(projection, BdTableMovimento._ID + "=?", new String[] { id }, null, null, null);
            default:
                throw new UnsupportedOperationException("URI inválida (QUERY): " + uri.toString());
        }
    }

    /**
     * Implement this to handle requests for the MIME type of the data at the
     * given URI.  The returned MIME type should start with
     * <code>vnd.android.cursor.item</code> for a single record,
     * or <code>vnd.android.cursor.dir/</code> for multiple items.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * <p>Note that there are no permissions needed for an application to
     * access this information; if your content provider requires read and/or
     * write permissions, or is not exported, all applications can still call
     * this method regardless of their access permissions.  This allows them
     * to retrieve the MIME type for a URI when dispatching intents.
     *
     * @param uri the URI to query.
     * @return a MIME type string, or {@code null} if there is no type.
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (getUriMatcher().match(uri)) {
            case URI_TIPO:
                return MULTIPLOS_ITEMS + TIPO;
            case URI_TIPO_UNICO:
                return UNICO_ITEM + TIPO;
            case URI_SERVICO:
                return MULTIPLOS_ITEMS + SERVICO;
            case URI_SERVICO_UNICO:
                return UNICO_ITEM + SERVICO;
            case URI_MOVIMENTO:
                return MULTIPLOS_ITEMS + MOVIMENTO;
            case URI_MOVIMENTO_UNICO:
                return UNICO_ITEM + MOVIMENTO;
            default:
                return null;
        }
    }

    /**
     * Implement this to handle requests to insert a new row.
     * As a courtesy, call {@link ContentResolver#notifyChange(Uri, ContentObserver) notifyChange()}
     * after inserting.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * @param uri    The content:// URI of the insertion request. This must not be {@code null}.
     * @param values A set of column_name/value pairs to add to the database.
     *               This must not be {@code null}.
     * @return The URI for the newly inserted item.
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase bd = bdNoteRegOpenHelper.getWritableDatabase();

        long id = -1 ;

        switch (getUriMatcher().match(uri)) {
            case URI_TIPO:
                id = new BdTableTipo(bd).insert(values);
                break;
            case URI_SERVICO:
                id = new BdTableServico(bd).insert(values);
                break;
            case URI_MOVIMENTO:
                id = new BdTableMovimento(bd).insert(values);
                break;
            default:
                throw new UnsupportedOperationException("URI inválida (INSERT):" + uri.toString());
        }
        if (id == -1) {
            throw new SQLException("Não foi possível inserir o registo");
        }
        return Uri.withAppendedPath(uri, String.valueOf(id));
    }

    /**
     * Implement this to handle requests to delete one or more rows.
     * The implementation should apply the selection clause when performing
     * deletion, allowing the operation to affect multiple rows in a directory.
     * As a courtesy, call {@link ContentResolver#notifyChange(Uri, ContentObserver) notifyChange()}
     * after deleting.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * <p>The implementation is responsible for parsing out a row ID at the end
     * of the URI, if a specific row is being deleted. That is, the client would
     * pass in <code>content://contacts/people/22</code> and the implementation is
     * responsible for parsing the record number (22) when creating a SQL statement.
     *
     * @param uri           The full URI to query, including a row ID (if a specific record is requested).
     * @param selection     An optional restriction to apply to rows when deleting.
     * @param selectionArgs
     * @return The number of rows affected.
     * @throws SQLException
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase bd = bdNoteRegOpenHelper.getWritableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_TIPO_UNICO:
                return new BdTableTipo(bd).delete(BdTableTipo._ID + "=?", new String[] {id});
            case URI_SERVICO_UNICO:
                return new BdTableServico(bd).delete(BdTableServico._ID + "=?", new String[] {id});
            case URI_MOVIMENTO_UNICO:
                return new BdTableMovimento(bd).delete(BdTableMovimento._ID + "=?", new String[] {id});
            default:
                throw new UnsupportedOperationException("URI inválida (DELETE): " + uri.toString());
        }

    }

    /**
     * Implement this to handle requests to update one or more rows.
     * The implementation should update all rows matching the selection
     * to set the columns according to the provided values map.
     * As a courtesy, call {@link ContentResolver#notifyChange(Uri, ContentObserver) notifyChange()}
     * after updating.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * @param uri           The URI to query. This can potentially have a record ID if this
     *                      is an update request for a specific record.
     * @param values        A set of column_name/value pairs to update in the database.
     *                      This must not be {@code null}.
     * @param selection     An optional filter to match rows to update.
     * @param selectionArgs
     * @return the number of rows affected.
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        SQLiteDatabase bd =  bdNoteRegOpenHelper.getWritableDatabase();

        String  id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_TIPO_UNICO:
                return new BdTableTipo(bd).update(values, BdTableTipo._ID + "=?", new String[] {id});
            case URI_SERVICO_UNICO:
                return new BdTableServico(bd).update(values, BdTableServico._ID + "=?", new String[] {id});
            case URI_MOVIMENTO_UNICO:
                return new BdTableMovimento(bd).update(values, BdTableMovimento._ID + "=?", new String[] {id});
            default:
                throw new UnsupportedOperationException("URI inválida (UPDATE): " + uri.toString());
        }

    }
}
