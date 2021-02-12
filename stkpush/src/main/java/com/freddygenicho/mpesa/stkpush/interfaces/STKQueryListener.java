package com.freddygenicho.mpesa.stkpush.interfaces;

import com.freddygenicho.mpesa.stkpush.api.response.STKPushResponse;



public interface STKQueryListener {

    void onResponse(STKPushResponse stkPushResponse);

    void onError(Throwable throwable);
}
