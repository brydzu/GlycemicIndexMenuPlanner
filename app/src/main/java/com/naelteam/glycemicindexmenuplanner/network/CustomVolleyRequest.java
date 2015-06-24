package com.naelteam.glycemicindexmenuplanner.network;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.naelteam.glycemicindexmenuplanner.provider.Parser;

/**
 * Created by fab on 02/06/15.
 */
public class CustomVolleyRequest<T> extends Request<T> {

    private Listener mListener;
    private Parser<T> mParser;

    public CustomVolleyRequest(int method, String url, Parser<T> parser, final Listener customListener) {
        super(method, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                customListener.onError(error);
            }
        });
        mListener = customListener;
        mParser = parser;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            T parsedData = mParser.parse(new String(response.data));
            return Response.success(parsedData, null);
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onSuccess(response);

    }

    public interface Listener<T>{
        public void onSuccess(T response);
        public void onError(VolleyError volleyError);
    }
}
