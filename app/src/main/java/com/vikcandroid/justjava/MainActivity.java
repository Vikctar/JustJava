package com.vikcandroid.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
            int price = quantity * 5;
            String priceMessage = "Total: $" + price;
            priceMessage = priceMessage + "\nThank you";
            displayPrice(priceMessage);

            calculatePrice(quantity, 10);
        } else {
            Toast.makeText(getApplicationContext(), "quantity cannot be a negative", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Calculates the price of the order
     *
     * @param quantity is the number of cups of coffee ordered
     * @param pricePerCup is the price of one cup of coffee
     */
    private void calculatePrice(int quantity, int pricePerCup) {
        int price = quantity * pricePerCup;
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
    private void displayPrice(String message) {
        TextView priceTextView = (TextView) findViewById(
                R.id.price_text_view
        );
        priceTextView.setText(message);
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
