package com.freddygenicho.mpesa.stkpush.interfaces;

import com.freddygenicho.mpesa.stkpush.model.Token;



public interface TokenListener {


    void onTokenSuccess(Token token);


    void OnTokenError(Throwable throwable);
}
