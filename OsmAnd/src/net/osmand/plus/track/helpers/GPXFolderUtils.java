package net.osmand.plus.track.helpers;

import androidx.annotation.NonNull;

import net.osmand.Collator;
import net.osmand.OsmAndCollator;
import net.osmand.plus.settings.enums.TracksSortByMode;

import java.io.File;
import java.util.Arrays;

public class GPXFolderUtils {

	public static String getSubfolderTitle(@NonNull File file, @NonNull String subfolder) {
		String name = file.getName();
		return subfolder.isEmpty() ? name : subfolder + File.separator + name;
	}

	@NonNull
	public static File[] listFilesSorted(@NonNull TracksSortByMode sortMode, @NonNull File dir) {
		File[] listFiles = dir.listFiles();
		if (listFiles == null) {
			return new File[0];
		}
		// This file could be sorted in different way for folders
		// now folders are also sorted by last modified date
		Collator collator = OsmAndCollator.primaryCollator();
		Arrays.sort(listFiles, (f1, f2) -> {
			if (sortMode == TracksSortByMode.BY_NAME_ASCENDING) {
				return collator.compare(f1.getName(), (f2.getName()));
			} else if (sortMode == TracksSortByMode.BY_NAME_DESCENDING) {
				return -collator.compare(f1.getName(), (f2.getName()));
			} else {
				// here we could guess date from file name '2017-08-30 ...' - first part date
				if (f1.lastModified() == f2.lastModified()) {
					return -collator.compare(f1.getName(), (f2.getName()));
				}
				return -(Long.compare(f1.lastModified(), f2.lastModified()));
			}
		});
		return listFiles;
	}

}
