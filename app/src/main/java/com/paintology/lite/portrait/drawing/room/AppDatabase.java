package com.paintology.lite.portrait.drawing.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.paintology.lite.portrait.drawing.room.conerters.Converters;
import com.paintology.lite.portrait.drawing.room.daos.ColorSwatchDao;
import com.paintology.lite.portrait.drawing.room.daos.FavDao;
import com.paintology.lite.portrait.drawing.room.daos.PaintingDao;
import com.paintology.lite.portrait.drawing.room.daos.PublishDao;
import com.paintology.lite.portrait.drawing.room.daos.SavedDrawingDao;
import com.paintology.lite.portrait.drawing.room.daos.SavedTutorialDao;
import com.paintology.lite.portrait.drawing.room.entities.ColorSwatchEntity;
import com.paintology.lite.portrait.drawing.room.entities.DrawingEntity;
import com.paintology.lite.portrait.drawing.room.entities.PaintingEntity;
import com.paintology.lite.portrait.drawing.room.entities.PublishEntity;
import com.paintology.lite.portrait.drawing.room.entities.SavedDrawingEntity;
import com.paintology.lite.portrait.drawing.room.entities.SavedTutorialEntity;

@Database(entities = {ColorSwatchEntity.class, SavedTutorialEntity.class , DrawingEntity.class , PaintingEntity.class, SavedDrawingEntity.class, PublishEntity.class}, version = 9)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ColorSwatchDao colorSwatchDao();

    public abstract SavedTutorialDao savedTutorialDao();
    public abstract SavedDrawingDao savedDrawingDao();
    public abstract FavDao drawingFavDao();
    public abstract PaintingDao paintingDao();
    public abstract PublishDao publishDao();

}
