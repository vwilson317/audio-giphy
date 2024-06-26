package com.example.customkeyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.inputmethodservice.Keyboard;
public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener
{

    public MyInputMethodService() {
        super();
    }

    private KeyboardView keyboardView;
    private Keyboard keyboard;

    private boolean caps = false;

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboard = new Keyboard(this, R.xml.qwerty);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection = getCurrentInputConnection();
        if (inputConnection != null) {
            switch(primaryCode) {

                case  Keyboard.KEYCODE_DELETE:
                    CharSequence selectedText = inputConnection.getSelectedText(0);

                    if (TextUtils.isEmpty(selectedText)) {
                        inputConnection.deleteSurroundingText(1, 0);
                    } else {
                        inputConnection.commitText("", 1);
                    }
                    break;
                case Keyboard.KEYCODE_SHIFT:
                    keyboard = new Keyboard(this, R.xml.qwerty);
                    keyboardView.setKeyboard(keyboard);
                    caps = !caps;
                    keyboard.setShifted(caps);
                    keyboardView.invalidateAllKeys();
                    break;
                case Keyboard.KEYCODE_MODE_CHANGE:
                    keyboard = new Keyboard(this, R.xml.keys_layout);
                    keyboardView.setKeyboard(keyboard);
                    break;
                case -10:
                    keyboard = new Keyboard(this, R.xml.more_keys_layout);
                    keyboardView.setKeyboard(keyboard);
                    break;
                case -11:
                    keyboard = new Keyboard(this, R.xml.keys_layout);
                    keyboardView.setKeyboard(keyboard);
                    break;
                case Keyboard.KEYCODE_DONE:
                    inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                    break;
                default :
                    char code = (char) primaryCode;
                    if(Character.isLetter(code) && caps){
                        code = Character.toUpperCase(code);
                    }
                    inputConnection.commitText(String.valueOf(code), 1);

            }
        }

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
