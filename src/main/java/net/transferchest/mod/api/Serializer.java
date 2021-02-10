package net.transferchest.mod.api;

import net.transferchest.mod.loader.TCLoader;

import java.io.*;

public final class Serializer
{
    public static final String NAME = "transferChest.data";
    public static final String PATH = "transfer_chest_mod";
    
    private static FileOutputStream outputStream;
    private static ObjectOutputStream objectOutputStream;
    private static FileInputStream inputStream;
    private static ObjectInputStream objectInputStream;
    
    public static boolean serialize(Object object, String worldName)
    {
        ModLogger logger = new ModLogger(TCLoader.MOD_ID);
        try
        {
            if(!fileExists(worldName))
            {
                File file = new File(getCompletePath(worldName));
                file.getParentFile().mkdirs();
                file.createNewFile();
                logger.logInfo("Data file still doesn't exist, generating a new one");
            }
            outputStream = new FileOutputStream(getCompletePath(worldName));
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(object);
            //logger.logInfo("Persistent chunks data successfully serialized");
            return true;
        } catch(IOException e)
        {
            logger.logError("Unable to serialize object, exception stack trace:");
            e.printStackTrace();
            return false;
        }
    }
    
    public static <T extends Object> T deserialize(String worldName)
    {
        ModLogger logger = new ModLogger(TCLoader.MOD_ID);
        try
        {
            if(!fileExists(worldName))
            {
                logger.logWarning("Object does not exist, nothing to deserialize");
                return null;
            }
            inputStream = new FileInputStream(getCompletePath(worldName));
            objectInputStream = new ObjectInputStream(inputStream);
            Object object = objectInputStream.readObject();
            try
            {
                T res = ((T) object);
                return res;
            } catch(Exception e)
            {
                logger.logError("Unable to cast deserialized data to type class");
                return null;
            }
        } catch(Exception e)
        {
            logger.logError("Unable to deserialize data, exception stack trace:");
            e.printStackTrace();
            return null;
        }
    }
    
    private static boolean fileExists(String worldName)
    {
        File file = new File(getCompletePath(worldName));
        return file.exists();
    }
    
    public static String getCompletePath(String worldName)
    {
        return "mods" + File.separator + PATH + File.separator + worldName + File.separator + NAME;
    }
}
