package comp3350.ei.presentation.orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import comp3350.ei.R;
import comp3350.ei.presentation.StoreProductParcel;

public class AskQuantity extends Activity {

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_quantity);
        final StoreProductParcel select = getIntent().getParcelableExtra("selected");

        Button buttonOk = this.findViewById(R.id.button_continue_add);
        Button buttonCancel = this.findViewById(R.id.button_cancel_add);
        buttonOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText quantity = findViewById(R.id.edit_ordered_quantity);
                String q = quantity.getText().toString();
                Intent intent = new Intent(AskQuantity.this, AddItemActivity.class);
                intent.putExtra("quantity", q);
                intent.putExtra("product", select);
                setResult(RESULT_OK, intent);
                AskQuantity.this.finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setResult(RESULT_CANCELED);
                AskQuantity.this.finish();
            }
        });
    }
}
