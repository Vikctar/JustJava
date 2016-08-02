package com.vikcandroid.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an oder form to order coffee
 */
public class MainActivity extends AppCompatActivity {

    // Initialize variable to hold quantity
    // This is a global variable
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked/touched
     */
    public void increment(View view) {

        quantity = quantity + 1;

        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked/touched
     */
    public void decrement(View view) {
        // int quantity = 2; The variable was local to this function before, I killed it :D

        quantity = quantity - 1;

        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked
     */
    public void submitOrder(View view) {
        if (quantity >= 0) {
            EditText nameField = (EditText) findViewById(R.id.edit_text_name);
            String name = nameField.getText().toString();

            // Find the check boxes in the view hierarchy
            CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream);
            CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);

            // store the checked state in a boolean
            boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
            boolean hasChocolate = chocolate.isChecked();

            int price = calculatePrice();
            String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
            displayMessage(priceMessage);
        } else {
            Toast.makeText(getApplicationContext(), "quantity cannot be a negative", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Calculates the price of the order
     *
     * @return total price
     */
    private int calculatePrice() {
        return quantity * 5;
    }

    /**
     * Create summary of the order
     *
     *
     * @param name of the customer
     * @param price of the order
     * @param addWhippedCream checks whether or not the user wants whipped cream topping
     * @param addChocolate whether or not the user wants chocolate topping
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = "Name: " + name + "\n";
        priceMessage += "Add whipped cream? " + addWhippedCream + "\n";
        priceMessage += "Add chocolate? " + addChocolate + "\n";
        priceMessage = priceMessage + "Quantity: " + quantity + "\n";
        priceMessage = priceMessage + "Total: $" + price;
        priceMessage = priceMessage + "\nThank you!";
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

    /**
     * This method displays the given price on the screen
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(
                R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
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
