package pcg.talkbackplus;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.graphics.Rect;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.widget.Toast;

import java.util.Locale;

import static android.accessibilityservice.AccessibilityService.SHOW_MODE_HIDDEN;

/**
 * Created by kinnplh on 2018/5/14.
 */

public class Utility {
    public static TextToSpeech tts;
    static boolean ttsPrepared;
    static TalkBackPlusService service;
    public static SoundPool soundPool;

    public static final Integer THREAD_MUTEX = 0;
    public static final int CMD_HIDE_OVERLAY = 0;
    public static final int CMD_SHOW_OVERLAY = CMD_HIDE_OVERLAY + 1;

    public final static int SOUND_SOU_PREVIOUS_ONE = 1;
    public final static int SOUND_SOU_NEXT_ONE = SOUND_SOU_PREVIOUS_ONE + 1;
    public final static int SOUND_DONG_ENTER = SOUND_SOU_NEXT_ONE + 1;
    public final static int SOUND_KOU_EXIT = SOUND_DONG_ENTER + 1;
    public final static int SOUND_CLICK = SOUND_KOU_EXIT + 1;
    public final static int SOUND_EMPTY_NOT_CLICKABLE = SOUND_CLICK + 1;
    public final static int SOUND_EMPTY_FIRST_OR_LAST = SOUND_EMPTY_NOT_CLICKABLE + 1;
    public final static int SOUND_DING = SOUND_EMPTY_FIRST_OR_LAST + 1;
    public final static int SOUND_BADA_NEW_PAGE = SOUND_DING + 1;

    public static void init(TalkBackPlusService s){
        Utility.service = s;
        if(tts == null){
            tts = new TextToSpeech(s, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    ttsPrepared = (i == TextToSpeech.SUCCESS);
                    if(!ttsPrepared){
                        tts = null;
                    } else {
                        tts.setLanguage(Locale.CHINESE);
                    }
                }
            });
        }
        if(soundPool==null)
        {
            soundPool = new SoundPool.Builder().build();
            soundPool.load(s, R.raw.sou, 1);//            public final static int SOUND_SOU_PREVIOUS_ONE = 1;
            soundPool.load(s, R.raw.sou_next, 1);//            public final static int SOUND_SOU_NEXT_ONE = SOUND_SOU_PREVIOUS_ONE + 1;
            soundPool.load(s, R.raw.dong, 1);//            public final static int SOUND_DONG_ENTER = SOUND_SOU_NEXT_ONE + 1;
            soundPool.load(s, R.raw.kou, 1);//            public final static int SOUND_KOU_EXIT = SOUND_DONG_ENTER + 1;
            soundPool.load(s, R.raw.click, 1);//            public final static int SOUND_CLICK = SOUND_KOU_EXIT + 1;
            soundPool.load(s, R.raw.empty_unclickable, 1);//            public final static int SOUND_EMPTY_NOT_CLICKABLE = SOUND_CLICK + 1;
            soundPool.load(s, R.raw.empty_last, 1);//            public final static int SOUND_EMPTY_FIRST_OR_LAST = SOUND_EMPTY_NOT_CLICKABLE + 1;
            //            public final static int SOUND_DING = SOUND_EMPTY_FIRST_OR_LAST + 1;
            //            public final static int SOUND_BADA_NEW_PAGE = SOUND_DING + 1;
        }

    }
    public static String printTree(AccessibilityNodeInfo root){
        StringBuffer res = new StringBuffer();
        printNodeStructure(root, 0, res);
        return res.toString();
    }
    public static void sendMsgToService(int cmd){
        Message m = new Message();
        m.arg1 = cmd;
        if(service==null || service.handler==null)
            return;
        service.handler.sendMessage(m);
    }


    public static void printNodeStructure(AccessibilityNodeInfo root, int depth, StringBuffer res){
        if(root == null){
            return;
        }
        root.refresh();
        Rect border = new Rect();
        root.getBoundsInScreen(border);
        for(int i = 0; i < depth; i ++){
            res.append("\t");
        }

        res.append(root.hashCode()).append(" ")
                .append(root.getClassName()).append(" ")
                .append(root.getViewIdResourceName()).append(" ")
                .append(border.toString()).append(" ")
                .append(root.getText()).append(" ")
                .append(root.getContentDescription()).append(" ")
                .append("isClickable: ").append(root.isClickable()).append(" ")
                .append("isScrollable: ").append(root.isScrollable()).append(" ")
                .append("isVisible: ").append(root.isVisibleToUser()).append(" ")
                .append("isEnabled: ").append(root.isEnabled()).append(" ")
                .append("labelBy: ").append((root.getLabeledBy() == null)? -1: root.getLabeledBy().hashCode()).append("\n");

        //res.append(root.toString()).append("\n");
        for(int i = 0; i < root.getChildCount(); ++ i){
            printNodeStructure(root.getChild(i), depth + 1, res);
        }
    }

    public static void shutdownTts(){
        if(tts != null){
            tts.shutdown();
            tts = null;
        }
    }

    public static void speak(String text){
        if(!ttsPrepared){
            Log.e("error", "speak: No tts available");
        } else {
            Log.i("info", "speak: " + text);
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "text to speak: " + text);
        }
    }
    public static boolean isNodeVisible(AccessibilityNodeInfo node){
        if(node == null){
            return false;
        }
        Rect r = new Rect();
        node.getBoundsInScreen(r);
        return r.width() > 0 && r.height() > 0;
    }
    public static boolean isNodeVisible(AccessibilityNodeInfoRecord node){
        if(node == null){
            return false;
        }
        Rect r = new Rect();
        node.getBoundsInScreen(r);
        return r.width() > 0 && r.height() > 0;
    }
    public static AccessibilityNodeInfo getNodeByNodeId(AccessibilityNodeInfo startNode, String relativeNodeId){
        if(startNode == null){
            return null;
        }
        int indexEnd = relativeNodeId.indexOf(';');
        if(indexEnd < 0){
            // 不存在分号，说明已经结束了
            if(startNode.getClassName().toString().equals(relativeNodeId)){
                return startNode;
            } else {
                return null;
            }
        }

        String focusPart = relativeNodeId.substring(0, indexEnd);
        int indexDivision = focusPart.indexOf('|');
        int childIndex = Integer.valueOf(focusPart.substring(indexDivision + 1));
        String crtPartClass = focusPart.substring(0, indexDivision);
        String remainPart = relativeNodeId.substring(indexEnd + 1);

        // 这里对 id 的处理方式，应该和 crawler 中的处理方式相一致的
        if(startNode.isScrollable() || (startNode.getClassName() != null && startNode.getClassName().toString().contains("ListView"))){
            int actualIndex = 0;
            while (actualIndex < startNode.getChildCount()) {
                if (isNodeVisible(startNode.getChild(actualIndex))) {
                    childIndex -= 1;
                    if (childIndex < 0) {
                        break;
                    }
                }
                actualIndex += 1;
            }
            childIndex = actualIndex;
        }

        if(startNode.getClassName().toString().equals(crtPartClass) && childIndex >= 0 && childIndex < startNode.getChildCount()){
            return getNodeByNodeId(startNode.getChild(childIndex), remainPart);
        } else {
            return null;
        }
    }

    public static void generateLayoutXML(AccessibilityNodeInfo crtRoot, int indexInParent, StringBuilder builder){
        // 生成描述这个节点及其子节点的 xml 字符串
        builder.append("<node ");
        appendField("index", indexInParent, builder);
        appendField("text", crtRoot.getText(), builder);
        appendField("resource-id", crtRoot.getViewIdResourceName(), builder);
        appendField("class", crtRoot.getClassName(), builder);
        appendField("package", crtRoot.getPackageName(), builder);
        appendField("content-desc", crtRoot.getContentDescription(), builder);
        appendField("checkable", crtRoot.isCheckable(), builder);
        appendField("checked", crtRoot.isChecked(), builder);
        appendField("clickable", crtRoot.isClickable(), builder);
        appendField("enabled", crtRoot.isEnabled(), builder);
        appendField("focusable", crtRoot.isFocusable(), builder);
        appendField("focused", crtRoot.isFocused(), builder);
        appendField("scrollable", crtRoot.isScrollable(), builder);
        appendField("long-clickable", crtRoot.isLongClickable(), builder);
        appendField("password", crtRoot.isPassword(), builder);
        appendField("selected", crtRoot.isSelected(), builder);
        appendField("editable", crtRoot.isEditable(), builder);
        appendField("accessibilityFocused", crtRoot.isAccessibilityFocused(), builder);
        appendField("dismissable", crtRoot.isDismissable(), builder);

        Rect r = new Rect();
        crtRoot.getBoundsInScreen(r);
        builder.append("bounds=\"").append('[').append(r.left).append(',').append(r.top).append("][").append(r.right).append(',').append(r.bottom).append(']').append('"');
        if(crtRoot.getChildCount() == 0){
            builder.append("/>");
        } else {
            builder.append(">");
            for(int i = 0; i < crtRoot.getChildCount(); ++ i){
                if(crtRoot.getChild(i) == null){
                    continue;
                }
                generateLayoutXML(crtRoot.getChild(i), i, builder);
            }
            builder.append("</node>");
        }
    }

    public static void generateLayoutXMLWithoutUselessNodes(AccessibilityNodeInfo crtRoot, int indexInParent, StringBuilder builder){
        assert indexInParent == 0;
        long start1 = System.currentTimeMillis();
        AccessibilityNodeInfoRecord root = new AccessibilityNodeInfoRecord(crtRoot, null, 0);
        long start2 = System.currentTimeMillis();
        Log.i("time spend", "build record " + String.valueOf(start2 - start1));
        root.ignoreUselessChild(false);
        long start3 = System.currentTimeMillis();
        Log.i("time spend", "ignore useless " + String.valueOf(start3 - start2));
        generateLayoutXMLWithRecord(root, 0, builder);
        long start4 = System.currentTimeMillis();
        Log.i("time spend", "get xml " + String.valueOf(start4 - start3));
    }

    public static void generateLayoutXMLWithRecord(AccessibilityNodeInfoRecord crtRoot, int indexInParent, StringBuilder builder){
        // 生成描述这个节点及其子节点的 xml 字符串
        builder.append("<node ");
        appendField("index", indexInParent, builder);
        appendField("text", crtRoot.getText(), builder);
        appendField("resource-id", crtRoot.getViewIdResourceName(), builder);
        appendField("class", crtRoot.getClassName(), builder);
        appendField("package", crtRoot.getPackageName(), builder);
        appendField("content-desc", crtRoot.getContentDescription(), builder);
        appendField("checkable", crtRoot.isCheckable(), builder);
        appendField("checked", crtRoot.isChecked(), builder);
        appendField("clickable", crtRoot.isClickable(), builder);
        appendField("enabled", crtRoot.isEnabled(), builder);
        appendField("focusable", crtRoot.isFocusable(), builder);
        appendField("focused", crtRoot.isFocused(), builder);
        appendField("scrollable", crtRoot.isScrollable(), builder);
        appendField("long-clickable", crtRoot.isLongClickable(), builder);
        appendField("password", crtRoot.isPassword(), builder);
        appendField("selected", crtRoot.isSelected(), builder);
        appendField("editable", crtRoot.isEditable(), builder);
        appendField("accessibilityFocused", crtRoot.isAccessibilityFocused(), builder);
        appendField("dismissable", crtRoot.isDismissable(), builder);

        Rect r = new Rect();
        crtRoot.getBoundsInScreen(r);
        builder.append("bounds=\"").append('[').append(r.left).append(',').append(r.top).append("][").append(r.right).append(',').append(r.bottom).append(']').append('"');
        if(crtRoot.getChildCount() == 0){
            builder.append("/>");
        } else {
            builder.append(">");
            for(int i = 0; i < crtRoot.getChildCount(); ++ i){
                if(crtRoot.getChild(i) == null){
                    continue;
                }
                generateLayoutXMLWithRecord(crtRoot.getChild(i), i, builder);
            }
            builder.append("</node>");
        }
    }

    static void appendField(String name, String value, StringBuilder builder){
        builder.append(name).append("=\"").append(value == null? "": intoXMLFormat(value)).append("\" ");
    }

    static void appendField(String name, int value, StringBuilder builder){
        builder.append(name).append("=\"").append(value).append("\" ");
    }

    static void appendField(String name, CharSequence value, StringBuilder builder){
        builder.append(name).append("=\"").append(value == null? "": intoXMLFormat(value)).append("\" ");
    }

    static void appendField(String name, boolean value, StringBuilder builder){
        builder.append(name).append("=\"").append(value? "true": "false").append("\" ");
    }

    static String intoXMLFormat(Object s){
        return s == null? "": s.toString().replace("\n", " ")
                .replace("&", "&amp;")
                .replace("\'", "&apos;")
                .replace("\"", "&quot;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("#", " ");
    }

    public static String dumpLayout()
    {
        service.getSoftKeyboardController().setShowMode(SHOW_MODE_HIDDEN);
        AccessibilityNodeInfo root = service.getRootInActiveWindow();
        AccessibilityNodeInfoRecord.buildTree();
        int try_time = 10;
        while (root == null && try_time > 0){
            root = service.getRootInActiveWindow();
            try_time -= 1;
        }
        if(root == null){
            return null;
        }
        StringBuilder xmlBuilder = new StringBuilder();
        Utility.generateLayoutXMLWithoutUselessNodes(root, 0, xmlBuilder);
        xmlBuilder.append("\n");
        service.getSoftKeyboardController().setShowMode(AccessibilityService.SHOW_MODE_AUTO);
        return xmlBuilder.toString();
    }

    public static String getNodeId(AccessibilityNodeInfo nodeInfo)
    {
        if(nodeInfo==null)
            return "";
        if(nodeInfo.getWindow().getType()== AccessibilityWindowInfo.TYPE_SYSTEM&&nodeInfo.getContentDescription()!=null)
        {
            return nodeInfo.getContentDescription().toString();
        }
        String nodeId = AccessibilityNodeInfoRecord.nodeInfoHashtoId.getOrDefault(nodeInfo.hashCode(),"");
        Log.d("nodeid",nodeInfo.toString() + nodeId);
        return nodeId;
    }

    public static void talk(Context context, String... args){
        StringBuilder sb = new StringBuilder();
        String temp = "";
        for (Object obj : args){
            if(obj!=null){
                temp = obj.toString();
            }else{
                temp = " *null* ";
            }
            sb.append(temp);
        }
        Toast.makeText(context,sb.toString(),Toast.LENGTH_SHORT).show();
    }

    public static void toastLogger(final String... args){
        Handler handler = new Handler(service.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                talk(service,args);
            }
        });
    }

    public static void playSound(int type) {
        soundPool.play(type,1,1,1,0,1);
    }


}

