/**
 * File     : MainActivity.java
 * Project  : TemplateActivity
 * Author   : Markus Jaton 2 juillet 2014
 * 			  Fabien Dutoit 20 septembre 2016
 *            IICT / HEIG-VD
 *                                       
 * mailto:fabien.dutoit@heig-vd.ch
 * 
 * This piece of code reads a [email_account / password ] combination.
 * It is used as a template project for the SYM module element given at HEIG-VD
 * Target audience : students IL, TS, IE [generally semester 1, third bachelor year]
 *   
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY 
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL 
 * THE REGENTS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */
package ch.heigvd.sym.template;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // For logging purposes
    private static final String TAG = MainActivity.class.getSimpleName();

	private HashMap<String, String> uPass = new HashMap<>();

    // GUI elements
	private EditText email      = null;
	private EditText psw      	= null;
    private Button   signIn     = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		uPass.put("toto@tutu.com", "tata");
		uPass.put("bob@bob", "bob");
		uPass.put("nope@nope", "nope");

		// Show the welcome screen / login authentication dialog
		setContentView(R.layout.authent);

		// Link to GUI elements
        this.email      = (EditText) findViewById(R.id.email);
		this.psw     	= (EditText) findViewById(R.id.psw);
        this.signIn     = (Button)   findViewById(R.id.buttOk);

		// Then program action associated to "Ok" button
		signIn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				String mail = email.getText().toString();
				String passwd = psw.getText().toString();

				if (isValid(mail, passwd)) {
					if (isKnown(mail, passwd)) {
						Toast.makeText(MainActivity.this, getResources().getString(R.string.good), Toast.LENGTH_LONG).show();

						Intent intent = new Intent(getApplicationContext(), ch.heigvd.sym.template.DisplayActivity.class);
						intent.putExtra("emailEntered", mail);
						intent.putExtra("passwordGiven", passwd);
						startActivity(intent);

						finish();
					} else {
						// Wrong combination, display pop-up dialog and stay on login screen
						showErrorDialog(mail, passwd);

						email.setText("");
						psw.setText("");
					}
				} else {
					Toast.makeText(MainActivity.this, getResources().getString(R.string.wrongEmail), Toast.LENGTH_LONG).show();
				}
			}
			
		});
	}

	/**
	 * @brief check if the mail and password aren't wrongly written
	 * @param mail String The mail written by the user
	 * @param passwd String The password written by the user
	 * @return true if the mail and password aren't null and mail contains an @, else false
	 */
	private boolean isValid(String mail, String passwd) {
        if(mail == null || passwd == null || !mail.contains("@")) {
            Log.w(TAG, "isValid(mail, passwd) - mail and passwd cannot be null !");
            return false;
        }
		// Return true if combination valid, false otherwise
		return true;
	}

	/**
	 * @brief Check if the mail and password combinaison exists in the "database" (hashmap here)
	 * @param mail String The mail (key in the hashmap)
	 * @param passwd String The password (value in the hashmap)
	 * @return true if the combinaison exists, else false
	 */
	private boolean isKnown(String mail, String passwd) {
		String pass = uPass.get(mail);
		if (pass != null) {
			return pass.equals(passwd);
		}
		return false;
	}
	
	protected void showErrorDialog(String mail, String passwd) {
		/*
		 * Pop-up dialog to show error
		 */
		AlertDialog.Builder alertbd = new AlertDialog.Builder(this);
        alertbd.setIcon(R.drawable.ic_highlight_off_black_24dp);
		alertbd.setTitle(R.string.wronglogin);
	    alertbd.setMessage(R.string.wrong);
	    alertbd.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // we do nothing...
                // dialog close automatically
	        }
	     });
	    alertbd.create().show();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
