package pcg.talkbackplus;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;


public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

    String Text_Id;


    public  TalkBackPlusEventType type = TalkBackPlusEventType.EVENT_NONE;



    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    public boolean onSingleTapConfirmed(MotionEvent e) {
        type=TalkBackPlusEventType.EVENT_DOWN;
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {

        int Down_x = (int) e.getRawX();
        int Down_y = (int) e.getRawY();
        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (Down_x > 1000 && Down_y < 100) {
                //Toast.makeText(MainActivity.this, "右上角双击", Toast.LENGTH_SHORT).show();
                Log.d("context", "--------右上角双击");
                type = TalkBackPlusEventType.EVENT_RIGHT_TOP_DOUBLE_CLICK;
            } else if (Down_x > 1000 && Down_y > 2000) {
                //Toast.makeText(MainActivity.this, "右下角双击", Toast.LENGTH_SHORT).show();
                Log.d("context", "--------右下角双击");
                Text_Id = "右下角双击";
                type = TalkBackPlusEventType.EVENT_RIGHT_BOTTOM_DOUBLE_CLICK;
            } else if (Down_x < 100 && Down_y < 100) {
                //Toast.makeText(MainActivity.this, "左上角双击", Toast.LENGTH_SHORT).show();
                Log.d("context", "--------左上角双击");
                Text_Id = " 左上角双击";
                type = TalkBackPlusEventType.EVENT_LEFT_TOP_DOUBLE_CLICK;
            } else if (Down_x < 100 && Down_y > 2000) {
                //Toast.makeText(MainActivity.this, "左下角双击", Toast.LENGTH_SHORT).show();
                Log.d("context", "--------左下角双击");
                Text_Id = "左下角双击";
                type = TalkBackPlusEventType.EVENT_LEFT_BOTTOM_DOUBLE_CLICK;
            } else if (Down_x > 1000 && (Down_y > 100 && Down_y < 2000)) {
                //Toast.makeText(MainActivity.this, "右边双击", Toast.LENGTH_SHORT).show();
                Log.d("context", "--------右边双击");
                Text_Id = "右边双击";
            } else {
                //Toast.makeText(MainActivity.this, "未识别", Toast.LENGTH_SHORT).show();
                Log.d("context", "--------未识别");
                Text_Id = null;
            }}
            return true;

    }



    public void onShowPress(MotionEvent e) { }

    public boolean onContextClick(MotionEvent e) {
        return true;
    }



    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        //Log.d(TAG, "onSingleTapUp:手指离开屏幕的一瞬间");
        //Toast.makeText(MainActivity.this, "离开屏幕一瞬间", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        //Log.d(TAG, "onScroll:在触摸屏上滑动");
        //Toast.makeText(MainActivity.this, "滑动", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        //Log.d(TAG, "onLongPress:长按并且没有松开");
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        /*      EVENT_LEFT_FLING_DOWN,
                EVENT_LEFT_FLING_UP,
                EVENT_RIGHT_FLING_DOWN,
                EVENT_RIGHT_FLING_UP,
                EVENT_TOP_FLING_LEFT,
                EVENT_TOP_FLING_RIGHT,
                EVENT_BOTTOM_FLING_LEFT,
                EVENT_BOTTOM_FLING_RIGHT,
                EVENT_LEFT_TOP_DOUBLE_CLICK,
                EVENT_LEFT_BOTTOM_DOUBLE_CLICK,
                EVENT_RIGHT_TOP_DOUBLE_CLICK,
                EVENT_RIGHT_BOTTOM_DOUBLE_CLICK,*/
        String TAG = toString();
        float offsetX = e1.getRawX() - e2.getRawX();
        float offsetY = e1.getRawY() - e2.getRawY();
        int newX = (int) e1.getRawX();
        int newY = (int) e1.getRawY();
        /*Log.d(TAG, "RAW_X=------------------" + newX);
        Log.d(TAG, "RAW_Y=------------------" + newY);
        Log.d(TAG, "velocity_Y=------------------" + velocityY);
        Log.d(TAG, "velocity_X=------------------" + velocityX);
        Log.d(TAG, "offset_X=-----------------+" + offsetX);
        Log.d(TAG, "offset_y=-----------------+" + offsetY);
*/
        if (e2.getAction() == MotionEvent.ACTION_UP) {
            if (Math.abs(offsetX) < 150 && Math.abs(offsetY) < 150) {
                if (newY < 150) {
                    if (offsetX < 0 && offsetY > 0 && newX > 1000) {
                        //Toast.makeText(MainActivity.this, "右上", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "--------右上");
                    } else if (offsetX > 0 && offsetY > 0 && newX < 200) {
                        //Toast.makeText(MainActivity.this, "左上", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "--------左上");
                    }
                } else if (newY > 2000) {
                    if (offsetX < 0 && offsetY < 0 && newX > 900) {
                        //Toast.makeText(MainActivity.this, "右下", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "--------右下");
                    } else if (offsetX > 0 && offsetY < 0 && newX < 200) {
                        //Toast.makeText(MainActivity.this, "左下", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "--------左下");
                    }
                }
            } else {
                if (Math.abs(offsetX) > Math.abs(offsetY)) {
                    if (offsetX > 0 && newY > 150 && newY < 2000) {
                        //Toast.makeText(MainActivity.this, "屏幕左滑", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "--------屏幕左滑");
                        Text_Id = "屏幕左滑";
                        type = TalkBackPlusEventType.EVENT_CENTER_FLING_LEFT;
                    } else if (offsetX > 0 && newY > 150 && newY > 2000) {
                        //Toast.makeText(MainActivity.this, "底部左滑", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "--------底部左滑");
                        Text_Id = "底部左滑";
                        type = TalkBackPlusEventType.EVENT_BOTTOM_FLING_LEFT;
                    } else if (newY < 150 && newX < 500) {
                        //Toast.makeText(MainActivity.this, "顶部右滑", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "--------顶部右滑");
                        Text_Id = "顶部右滑";
                        type = TalkBackPlusEventType.EVENT_TOP_FLING_RIGHT;
                    } else if (newY > 150 && newX < 500 && newY < 2000) {
                        //Toast.makeText(MainActivity.this, "屏幕右滑", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "--------屏幕右滑");
                        Text_Id = "屏幕右滑";
                        type = TalkBackPlusEventType.EVENT_CENTER_FLING_RIGHT;
                    } else if (newY > 150 && newX < 500 && newY > 2000) {
                        //Toast.makeText(MainActivity.this, "底部右滑", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "--------底部右滑");
                        Text_Id = "底部右滑";
                        type = TalkBackPlusEventType.EVENT_BOTTOM_FLING_RIGHT;
                    }

                } else {
                    if (offsetY > 0) {
                        if (newY < 1160) {
                            if (newX < 100 && newY > 500) {
                                //Toast.makeText(MainActivity.this, "上方屏幕左侧上滑", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "--------上方屏幕左侧上滑");
                                Text_Id = "上方屏幕左侧上滑";
                                type = TalkBackPlusEventType.EVENT_LEFT_FLING_UP;
                            } else if (newX > 1000 && newY > 500) {
                                //Toast.makeText(MainActivity.this, "上方屏幕右侧上滑", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "--------上方屏幕右侧上滑");
                                Text_Id = "屏幕右侧上滑";
                                type = TalkBackPlusEventType.EVENT_RIGHT_FLING_UP;
                            } else if (newY > 500) {
                                //Toast.makeText(MainActivity.this, "上方屏幕上滑", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "--------上方屏幕上滑");
                                Text_Id = "屏幕上滑";
                                type = TalkBackPlusEventType.EVENT_CENTER_FLING_UP;
                            }
                        } else {
                            if (newX < 100 && newY < 2000) {
                                //Toast.makeText(MainActivity.this, "下方屏幕左侧上滑", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "--------下方屏幕左侧上滑");
                                Text_Id = "下方屏幕左侧上滑";
                                type = TalkBackPlusEventType.EVENT_LEFT_FLING_UP;
                            } else if (newX > 1000 && newY < 2000) {
                                //Toast.makeText(MainActivity.this, "下方屏幕右侧上滑", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "--------下方屏幕右侧上滑");
                                Text_Id = "下方屏幕右侧上滑";
                                type = TalkBackPlusEventType.EVENT_RIGHT_FLING_UP;
                            } else if (newY < 2000) {
                                //Toast.makeText(MainActivity.this, "下方屏幕上滑", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "--------下方屏幕上滑");
                                Text_Id = "屏幕上滑";
                                type = TalkBackPlusEventType.EVENT_CENTER_FLING_UP;
                            }
                        }

                    } else {
                        if (newY < 1160) {
                            if (newX < 100 && newY > 300) {
                                //Toast.makeText(MainActivity.this, "上方屏幕左侧下滑", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "--------上方屏幕左侧下滑");
                                Text_Id = "上方屏幕左侧下滑";
                                type = TalkBackPlusEventType.EVENT_LEFT_FLING_DOWN;
                            } else if (newX > 1000 && newY > 300) {
                                //Toast.makeText(MainActivity.this, "上方屏幕右侧下滑", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "--------上方屏幕右侧下滑");
                                Text_Id = "上方屏幕右侧下滑";
                                type = TalkBackPlusEventType.EVENT_RIGHT_FLING_DOWN;
                            } else if (newY > 300) {
                                //Toast.makeText(MainActivity.this, "上方屏幕下滑", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "--------上方屏幕下滑");
                                Text_Id = "屏幕下滑";
                                type = TalkBackPlusEventType.EVENT_CENTER_FLING_DOWN;
                            }
                        } else {
                            if (newX < 100 && newY < 2000) {
                                //Toast.makeText(MainActivity.this, "下方屏幕左侧下滑", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "--------下方屏幕左侧下滑");
                                Text_Id = "下方屏幕左侧下滑";
                                type = TalkBackPlusEventType.EVENT_LEFT_FLING_DOWN;
                            } else if (newX > 1000 && newY < 2000) {
                                //Toast.makeText(MainActivity.this, "下方屏幕右侧下滑", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "--------下方屏幕右侧下滑");
                                Text_Id = "下方屏幕右侧下滑";
                                type = TalkBackPlusEventType.EVENT_RIGHT_FLING_DOWN;
                            } else if (newY < 2000) {
                                //Toast.makeText(MainActivity.this, "下方屏幕下滑", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "--------下方屏幕下滑");
                                Text_Id = "屏幕下滑";
                                type = TalkBackPlusEventType.EVENT_CENTER_FLING_DOWN;
                            }
                        }

                    }
                }

            }

        }
        return true;
    }



}

