import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Intent;

import com.example.ahmad_banking.Withdraw;

import org.junit.Before;
import org.junit.Test;

public class testcaseWithdraw {

    private Withdraw withdrawActivity = new Withdraw();

    @Before
    public void setUp() {
    }

    @Test
    public void testValidateAmount_WhenAmountIsEmpty_ShouldReturnFalse() {
        // Set up the test: Simulate an empty amount field
        withdrawActivity.withdraw_amount.setText("");

        // Call the method to be tested
        boolean result = withdrawActivity.validateAmount();

        // Assert the result
        assertFalse(result);
    }

    @Test
    public void testValidateAmount_WhenAmountIsNotEmpty_ShouldReturnTrue() {
        // Set up the test: Simulate a non-empty amount field
        withdrawActivity.withdraw_amount.setText("50");

        // Call the method to be tested
        boolean result = withdrawActivity.validateAmount();

        // Assert the result
        assertTrue(result);
    }

    // Add more test cases for validateAmount if needed

    @Test
    public void testCallWithdrawDashboard_WhenAmountIsValid_ShouldNavigateToDashboard() {
        // Set up the test: Simulate a valid amount and intent data
        withdrawActivity.withdraw_amount.setText("50");
        Intent intent = new Intent();
        intent.putExtra("userName", "testUser");
        withdrawActivity.intent = intent;

        // Call the method to be tested
        withdrawActivity.callWithdrawDashboard(null);

        // Assert the results, such as verifying if the intent was correctly set and if a toast message is displayed
        assertEquals();
    }
}
