package map;

import org.domogik.domodroid.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class Dialog_Move extends Dialog implements OnClickListener {
	private Button okButton;

	public Dialog_Move(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/** Design the dialog in main.xml file */
		setContentView(R.layout.dialog_move);
		okButton = (Button) findViewById(R.id.OkButton);
		okButton.setOnClickListener(this);

	}

	
	public void onClick(View v) {
		/** When OK Button is clicked, dismiss the dialog */
		if (v == okButton)
			dismiss();
	}
}