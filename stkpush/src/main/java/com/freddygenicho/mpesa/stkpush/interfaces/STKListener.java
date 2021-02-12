package com.freddygenicho.mpesa.stkpush.interfaces;

import com.freddygenicho.mpesa.stkpush.api.response.STKPushResponse;



public interface STKListener {

    void onResponse(STKPushResponse stkPushResponse);

    void onError(Throwable throwable);
}
