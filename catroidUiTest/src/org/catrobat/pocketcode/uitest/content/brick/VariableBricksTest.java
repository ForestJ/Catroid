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
package org.catrobat.pocketcode.uitest.content.brick;

import org.catrobat.pocketcode.ProjectManager;
import org.catrobat.pocketcode.R;
import org.catrobat.pocketcode.uitest.util.UiTestUtils;
import org.catrobat.pocketcode.content.Project;
import org.catrobat.pocketcode.content.Script;
import org.catrobat.pocketcode.content.Sprite;
import org.catrobat.pocketcode.content.StartScript;
import org.catrobat.pocketcode.content.bricks.ChangeVariableBrick;
import org.catrobat.pocketcode.content.bricks.SetVariableBrick;
import org.catrobat.pocketcode.formulaeditor.UserVariablesContainer;
import org.catrobat.pocketcode.stage.StageActivity;
import org.catrobat.pocketcode.ui.MainMenuActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import android.widget.Spinner;

import com.jayway.android.robotium.solo.Solo;

public class VariableBricksTest extends ActivityInstrumentationTestCase2<MainMenuActivity> {
	private Solo solo;
	private Project project;
	private UserVariablesContainer userVariablesContainer;
	private SetVariableBrick setVariableBrick;
	private ChangeVariableBrick changeVariableBrick;
	private Sprite sprite;

	public VariableBricksTest() {
		super(MainMenuActivity.class);
	}

	@Override
	public void setUp() throws Exception {
		createProject();
		solo = new Solo(getInstrumentation(), getActivity());
		UiTestUtils.prepareStageForTest();
		UiTestUtils.getIntoScriptActivityFromMainMenu(solo);
	}

	@Override
	public void tearDown() throws Exception {
		UiTestUtils.goBackToHome(getInstrumentation());
		solo.finishOpenedActivities();
		UiTestUtils.clearAllUtilTestProjects();
		userVariablesContainer.deleteUserVariableByName("p1");
		userVariablesContainer.deleteUserVariableByName("p2");
		userVariablesContainer.deleteUserVariableByName("sprite_var1");
		userVariablesContainer.deleteUserVariableByName("sprite_var2");
		super.tearDown();
		solo = null;
	}

	@Smoke
	public void testVariableBricks() {
		Spinner setVariableSpinner = solo.getCurrentViews(Spinner.class).get(0);
		Spinner changeVariableSpinner = solo.getCurrentViews(Spinner.class).get(1);

		solo.clickOnView(setVariableSpinner);
		solo.clickOnText("p2");
		solo.clickOnView(changeVariableSpinner);
		solo.clickOnText("p2", 1);

		//		UiTestUtils.testBrickWithFormulaEditor(solo, 0, 1, 50, "variable_formula", setVariableBrick);
		solo.clickOnText("0");
		UiTestUtils.insertIntegerIntoEditText(solo, 50);
		solo.goBack();

		//		UiTestUtils.testBrickWithFormulaEditor(solo, 0, 1, -8, "variable_formula", changeVariableBrick);
		solo.clickOnText("1");
		UiTestUtils.insertDoubleIntoEditText(solo, -8.0);
		solo.goBack();

		solo.waitForView(solo.getView(R.id.button_play));
		UiTestUtils.clickOnBottomBar(solo, R.id.button_play);
		solo.waitForActivity(StageActivity.class.getSimpleName());
		solo.sleep(1500);

		assertEquals("Variable has the wrong value after stage", 42.0,
				userVariablesContainer.getUserVariable("p2", sprite).getValue());

	}

	private void createProject() {
		project = new Project(null, UiTestUtils.DEFAULT_TEST_PROJECT_NAME);
		sprite = new Sprite("cat");
		Script script = new StartScript(sprite);
		ProjectManager.getInstance().setProject(project);
		ProjectManager.getInstance().setCurrentSprite(sprite);
		ProjectManager.getInstance().setCurrentScript(script);

		userVariablesContainer = project.getUserVariables();
		userVariablesContainer.addProjectUserVariable("p1");
		userVariablesContainer.addProjectUserVariable("p2");
		userVariablesContainer.addSpriteUserVariable("sprite_var1");
		userVariablesContainer.addSpriteUserVariable("sprite_var2");

		setVariableBrick = new SetVariableBrick(sprite, 0.0);
		script.addBrick(setVariableBrick);
		changeVariableBrick = new ChangeVariableBrick(sprite, 1.1);
		script.addBrick(changeVariableBrick);

		sprite.addScript(script);
		project.addSprite(sprite);
	}

}