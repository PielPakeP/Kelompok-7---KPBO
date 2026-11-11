import greenfoot.*;

/**
 * Induk semua World di game ini (pewarisan / inheritance).
 * Semua layar berukuran 900x600 dan otomatis mem-pause / melanjutkan musik
 * saat tombol Pause / Run ditekan.
 */
public abstract class BaseWorld extends World
{
    public BaseWorld()
    {
        super(900, 600, 1);
    }
    
    public void stopped()
    {
        SoundManager.pause();
    }
    
    public void started()
    {
        SoundManager.resume();
    }
}
