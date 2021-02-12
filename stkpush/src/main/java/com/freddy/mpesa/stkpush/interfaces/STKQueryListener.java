package com.freddy.mpesa.stkpush.interfaces;

import com.freddy.mpesa.stkpush.api.response.STKPushResponse;



public interface STKQueryListener {

    void onResponse(STKPushResponse stkPushResponse);

    void onError(Throwable throwable);
}
