package com.vikcandroid.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an oder form to order coffee
 */
public class MainActivity extends AppCompatActivity {

    // Initialize variable to hold quantity
    // This is a global variable
    private int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked/touched
     */
    public void increment(View view) {

        if (quantity == 100) {
            // show an error message as a toast
            Toast.makeText(getBaseContext(), "you cannot have more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing to do
            return;
        }

        quantity = quantity + 1;

        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked/touched
     */
    public void decrement(View view) {
        // int quantity = 2; The variable was local to this function before, I killed it :D

        if (quantity == 1) {
            // Toast an error message
            Toast.makeText(this, "You cannot order less than 1 cup", Toast.LENGTH_SHORT).show();
            // Exit the method early because there is nothing to do
            return;
        }

        quantity = quantity - 1;

        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked
     */
    public void submitOrder(View view) {
        if (quantity > 0) {
            EditText nameField = (EditText) findViewById(R.id.edit_text_name);
            String name = nameField.getText().toString();

            // Find the check boxes in the view hierarchy
            CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream);
            CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);

            // store the checked state in a boolean
            boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
            boolean hasChocolate = chocolate.isChecked();

            // Calculate the price
            int price = calculatePrice(hasWhippedCream, hasChocolate);
            String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

            // Use an intent to launch email app
            // send the order summary in the email body
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, "support@bpg.co.ke");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
            intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        } else if (quantity == 0) {
            Toast.makeText(this, "Please order something", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "quantity cannot be a negative", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Calculates the price of the order
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // Price of 1 cup of coffee
        int basePrice = 5;

        // Add $1 if the user wants whipped cream
        if (addWhippedCream) {
            basePrice += 1;
        }

        // Add $2 if the user wants chocolate
        if (addChocolate) {
            basePrice += 2;
        }

        // Calculate the total order price by multiplying by quantity
        return quantity * basePrice;
    }

    /**
     * Create summary of the order
     *
     * @param name            of the customer
     * @param price           of the order
     * @param addWhippedCream checks whether or not the user wants whipped cream topping
     * @param addChocolate    whether or not the user wants chocolate topping
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name) + "\n";
        priceMessage += getString(R.string.order_summary_whipped_cream, addWhippedCream) + "\n";
        priceMessage += getString(R.string.order_summary_chocolate, addChocolate) + "\n";
        priceMessage += getString(R.string.order_summary_quantity, quantity) + "\n";
        priceMessage += getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);

        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view
        );
        quantityTextView.setText("" + numberOfCoffees);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
