package com.geocar.alex.geocarapp.web;

/**
 * Created by Badgerati on 24/10/2015.
 */
public interface IAsyncTask<V, U, T>
{

    interface OnPostExecuteListener
    {
        <T> void onPostExecute(IAsyncTask asyncTask, T result, String tag);
    }

    interface OnPreExecuteListener
    {
        void onPreExecute(IAsyncTask asyncTask, String tag);
    }

    void setOnPostExecuteListener(OnPostExecuteListener listener);
    void setOnPreExecuteListener(OnPreExecuteListener listener);

    T doInBackground(V... objects);
    void onPostExecute(T result);
    void onPreExecute();
    void executor(V... objects);

}
