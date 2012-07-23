/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010  Catroid development team 
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.tugraz.ist.catroid.xml.serializer;

import java.util.ArrayList;
import java.util.List;

import at.tugraz.ist.catroid.common.SoundInfo;

public class SoundSerializer extends Serializer {

	@Override
	public List<String> serialize(Object object) throws IllegalArgumentException, IllegalAccessException,
			SecurityException, NoSuchFieldException {
		SoundInfo soundInfo = (SoundInfo) object;
		String costumeFileName = soundInfo.getSoundFileName();
		String costumeName = soundInfo.getTitle();
		List<String> soundStringList = new ArrayList<String>();
		String xmlElementString = "";
		xmlElementString = getStartTag("Common.SoundInfo");
		soundStringList.add(xmlElementString);
		xmlElementString = getElementString("fileName", costumeFileName);
		soundStringList.add(xmlElementString);
		xmlElementString = getElementString("name", costumeName);
		soundStringList.add(xmlElementString);
		xmlElementString = getEndTag("Common.SoundInfo");
		soundStringList.add(xmlElementString);

		return soundStringList;
	}

	public List<String> serializeSoundList(List<SoundInfo> soundList) throws IllegalArgumentException,
			SecurityException, IllegalAccessException, NoSuchFieldException {
		List<String> soundStrings = new ArrayList<String>();
		soundStrings.add(getStartTag("soundList"));
		for (SoundInfo soundInfo : soundList) {
			soundStrings.addAll(this.serialize(soundInfo));
		}
		soundStrings.add(getEndTag("soundList"));
		return soundStrings;
	}

}