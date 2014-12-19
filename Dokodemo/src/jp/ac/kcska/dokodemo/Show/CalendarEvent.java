package jp.ac.kcska.dokodemo.Show;

public class CalendarEvent {
    // イベントID
    private long mId;
    // イベントタイトル
    private String mTitle;

    public CalendarEvent(long id) {
        mId = id;
    }

    /***
     * イベントIDを取得
     * 
     * @return
     */
    public long getId() {
        return mId;
    }

    /***
     * イベントIDを設定
     * 
     * @param id
     */
    public void setId(long id) {
        mId = id;
    }

    /***
     * イベントタイトルを取得
     * 
     * @return
     */
    public String getTitle() {
        return mTitle;
    }

    /***
     * イベントタイトルを設定
     * 
     * @param title
     */
    public void setTitle(String title) {
        mTitle = title;
    }

}
