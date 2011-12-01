/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010-2011 The Catroid Team
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://www.catroid.org/catroid_license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *   
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.tugraz.ist.catroid.content.bricks;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import at.tugraz.ist.catroid.R;
import at.tugraz.ist.catroid.content.Sprite;
import at.tugraz.ist.catroid.ui.dialogs.RotationDialog;

public class TurnRightBrick implements Brick, OnClickListener {

	private static final long serialVersionUID = 1L;

	private Sprite sprite;

	private double degrees;

	private transient View view;

	public TurnRightBrick(Sprite sprite, double degrees) {
		this.sprite = sprite;
		this.degrees = degrees;
	}

	public int getRequiredResources() {
		return NO_RESOURCES;
	}

	public void execute() {
		sprite.costume.rotation = (sprite.costume.rotation % 360) - (float) degrees;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public View getView(Context context, int brickId, BaseAdapter adapter) {

		view = View.inflate(context, R.layout.toolbox_brick_turn_right, null);

		EditText editDegrees = (EditText) view.findViewById(R.id.toolbox_brick_turn_right_edit_text);
		editDegrees.setText(String.valueOf(degrees));

		editDegrees.setOnClickListener(this);

		return view;
	}

	public View getPrototypeView(Context context) {
		return View.inflate(context, R.layout.toolbox_brick_turn_right, null);
	}

	@Override
	public Brick clone() {
		return new TurnRightBrick(getSprite(), degrees);
	}

	private void updateBrickView() {
		EditText editDegrees = (EditText) view.findViewById(R.id.toolbox_brick_turn_right_edit_text);
		editDegrees.setText(String.valueOf(degrees));
	}

	public void onClick(View view) {
		final Context context = view.getContext();

		final EditText input = new EditText(context);
		input.setText(String.valueOf(degrees));
		input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		input.setSelectAllOnFocus(true);

		Button okButton = new Button(context);
		okButton.setText("OK");

		final RotationDialog dialog = new RotationDialog(context, input, okButton, true);

		okButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				degrees = Double.parseDouble(input.getText().toString());
				updateBrickView();
				dialog.cancel();
			}
		});

		dialog.show();

	}

}
