/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.pocketcode.ui.dialogs;

import java.util.ArrayList;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import org.catrobat.pocketcode.ProjectManager;
import org.catrobat.pocketcode.R;
import org.catrobat.pocketcode.common.LookData;
import org.catrobat.pocketcode.io.StorageHandler;
import org.catrobat.pocketcode.ui.ScriptActivity;

public class DeleteLookDialog extends DialogFragment {

	private static final String BUNDLE_ARGUMENTS_SELECTED_POSITION = "selected_position";
	public static final String DIALOG_FRAGMENT_TAG = "dialog_delete_look";

	public static DeleteLookDialog newInstance(int selectedPosition) {
		DeleteLookDialog dialog = new DeleteLookDialog();

		Bundle arguments = new Bundle();
		arguments.putInt(BUNDLE_ARGUMENTS_SELECTED_POSITION, selectedPosition);
		dialog.setArguments(arguments);

		return dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final int selectedPosition = getArguments().getInt(BUNDLE_ARGUMENTS_SELECTED_POSITION);

		Dialog dialog = new AlertDialog.Builder(getActivity()).setTitle(R.string.delete_look_dialog)
				.setNegativeButton(R.string.cancel_button, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dismiss();
					}
				}).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						handleDeleteLook(selectedPosition);
					}
				}).create();

		dialog.setCanceledOnTouchOutside(true);

		return dialog;
	}

	private void handleDeleteLook(int position) {
		ArrayList<LookData> lookDataList = ProjectManager.getInstance().getCurrentSprite().getLookDataList();

		StorageHandler.getInstance().deleteFile(lookDataList.get(position).getAbsolutePath());
		lookDataList.remove(position);

		getActivity().sendBroadcast(new Intent(ScriptActivity.ACTION_LOOK_DELETED));
	}
}