package jp.ac.kcska.dokodemo.Show;

public class CalendarEvent {
    // �C�x���gID
    private long mId;
    // �C�x���g�^�C�g��
    private String mTitle;

    public CalendarEvent(long id) {
        mId = id;
    }

    /***
     * �C�x���gID���擾
     * 
     * @return
     */
    public long getId() {
        return mId;
    }

    /***
     * �C�x���gID��ݒ�
     * 
     * @param id
     */
    public void setId(long id) {
        mId = id;
    }

    /***
     * �C�x���g�^�C�g�����擾
     * 
     * @return
     */
    public String getTitle() {
        return mTitle;
    }

    /***
     * �C�x���g�^�C�g����ݒ�
     * 
     * @param title
     */
    public void setTitle(String title) {
        mTitle = title;
    }

}
