package com.freddy.sample.mpesa;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.e_funeral.Model.Cart;
//import com.example.e_funeral.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.freddy.sample.mpesa.Model.Cart;
import com.freddy.sample.mpesa.ViewHolder.CartViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessbtn , Showtotalprice,paybtn;
    private TextView txtToatalAmount;
    //String productID="";
    private int overallTotalPrice = 0;
    private int PAYPAL_REQ_CODE=999;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private FirebaseAuth mAuth;
    private String currentUserId;
    //.environment_production(going live)
    private static PayPalConfiguration config=new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(PaypalClientIDConfigClass.PAYPAL_CLIENT_ID);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();

        recyclerView=findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(false);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        NextProcessbtn=(Button)findViewById(R.id.next_process_button);
        txtToatalAmount=(TextView)findViewById(R.id.total_price);
        Showtotalprice=(Button)findViewById(R.id.show_total_price);
       // paybtn=findViewById(R.id.pay);
        Intent intent=new Intent(CartActivity.this,PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);
        //productID=getIntent().getStringExtra("pid");
        NextProcessbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //left ou totoal price

                Intent intent=new Intent(CartActivity.this,ConfirmFinalOrderActivity.class);
                intent.putExtra("Total Price", String.valueOf(overallTotalPrice));
                startActivity(intent);
                finish();


            }
        });

        Showtotalprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //left out toalprice
                txtToatalAmount.setText("Total Price = $" + String.valueOf(overallTotalPrice));

            }
        });
       /* paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaypalPaymentMethod();
            }
        });*/

    }

   /* private void PaypalPaymentMethod() {
        PayPalPayment payment=new PayPalPayment(new BigDecimal(overallTotalPrice),"USD","Payment For Ordered Items",PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent=new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment);
        startActivityForResult(intent,PAYPAL_REQ_CODE);


    }*/

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == Activity.RESULT_OK)
        {
            PaymentConfirmation confirmation=data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirmation != null){
                Intent intent=new Intent(this,ConfirmationActivity.class);
                startActivity(intent);
                try{
                    Log.i("paymentExample",confirmation.toJSONObject().toString(4));
                    Toast.makeText(CartActivity.this,"Payment Made Successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, ConfirmationActivity.class)
                    .putExtra("PaymentAmount", overallTotalPrice));
                            //.putExtra("PaymentAmount", paymentAmount));




                }catch (JSONException e){
                    Log.e("PaymentExample", "error occurred",e);
                    Toast.makeText(CartActivity.this,"Payment Unsuccessfull",Toast.LENGTH_LONG).show();
                }

            }
        }
        else if(resultCode == Activity.RESULT_CANCELED){
            Log.i("PaymentExample","The user Canceled");
        }
        else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            Log.i("PaymentExample","iNVALID PAYMENT");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/
    /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PAYPAL_REQ_CODE){
            if (requestCode == Activity.RESULT_OK){
                PaymentConfirmation confirmation=data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation == null){
                   // Intent intent=new Intent(this,ConfirmationActivity.class);
                   // startActivity(intent);
                    String state=confirmation.getProofOfPayment().getState();
                    if(state.equals("approved")){
                        txtToatalAmount.setText("payment approved");
                        Toast.makeText(CartActivity.this,"Payment Made Successfully",Toast.LENGTH_LONG).show();

                    }
                    else{
                        txtToatalAmount.setText("not paid");
                        Toast.makeText(CartActivity.this,"Payment is Unsuccesful",Toast.LENGTH_LONG).show();
                    }
                    }


            }
           // else{
              //  txtToatalAmount.setText("not paid");
              //  Toast.makeText(CartActivity.this,"Payment is Unsuccesful",Toast.LENGTH_LONG).show();

            }

        }*/
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode == PAYPAL_REQ_CODE) {
          if (resultCode == Activity.RESULT_OK) {
              PaymentConfirmation confirm = data
                      .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
              if (confirm != null) {
                  try {
                      System.out.println(confirm.toJSONObject().toString(4));
                      System.out.println(confirm.getPayment().toJSONObject()
                              .toString(4));
                      txtToatalAmount.setText("Payment made successfully");

                      Toast.makeText(getApplicationContext(), "Payment Made Successfully",
                              Toast.LENGTH_LONG).show();

                  } catch (JSONException e) {
                      e.printStackTrace();
                  }
              }
          } else if (resultCode == Activity.RESULT_CANCELED) {
              System.out.println("The user canceled.");
          } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
              System.out
                      .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
          }

          } else if (resultCode == Activity.RESULT_CANCELED) {
              Log.i("FuturePaymentExample", "The user canceled.");
          } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
              Log.i("FuturePaymentExample",
                      "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
          }
      }



    @Override
    protected void onStart() {
        super.onStart();


        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
        //left out the current user id in the next line(just incase)

        FirebaseRecyclerOptions<Cart> option=new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child("User View").child(currentUserId).child("Products"),Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter= new FirebaseRecyclerAdapter<Cart, CartViewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model)
            {
                holder.txtProductQuantity.setText( "Quantity = "+ model.getQuantity());
                holder.txtProductPrice.setText("Price = "+ model.getPrice() + "$" );
                holder.txtProductName.setText(model.getPname());
                int oneTypeProductPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                overallTotalPrice=overallTotalPrice + oneTypeProductPrice;

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"

                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options:");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if(i==0){
                                    Intent intent=new Intent(CartActivity.this,ProductDetailsActivity.class);
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);

                                }
                                else if(i==1){
                                    cartListRef.child("User View").child(currentUserId).child("Products").child(model.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(CartActivity.this,"Item removed successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent=new Intent(CartActivity.this,MainActivity2.class);
                                                startActivity(intent);
                                            }

                                        }
                                    });
                                }

                            }
                        });
                        builder.show();


                    }
                });



            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
              View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
              CartViewHolder holder=new CartViewHolder(view);
              return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

   /* @Override
    protected void onDestroy() {
        stopService(new Intent(CartActivity.this,PayPalService.class));
        super.onDestroy();
    }*/
}
