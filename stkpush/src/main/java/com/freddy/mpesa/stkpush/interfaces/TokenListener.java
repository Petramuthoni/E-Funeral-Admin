package com.freddy.mpesa.stkpush.interfaces;

import com.freddy.mpesa.stkpush.model.Token;



public interface TokenListener {


    void onTokenSuccess(Token token);


    void OnTokenError(Throwable throwable);
}
