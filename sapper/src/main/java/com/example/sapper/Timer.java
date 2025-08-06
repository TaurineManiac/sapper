package com.example.sapper;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.awt.event.ActionEvent;

public class Timer {
    Timeline timeline;
    Label min = new Label("00");
    Label sec = new Label("00");
    Font minecraftFont;


    //не забудь сократить потом ифы в последующих обновах

    //крч какова логика:мы создаё1м переменную таймера, как часто будет этот эвент повторятся,ит сам эвент мы берём
    // и прописыаем, как аноноимный класс
    public void setFont(Font font) {
        this.minecraftFont = font;
        // Применяем шрифт к существующим Label
        if (min != null) min.setFont(font);
        if (sec != null) sec.setFont(font);
    }

    public void resetTimer() {
        if(timeline == null){
            play();
        }
        else{
            stop();
            sec.setText("00");
            min.setText("00");
            play();
        }
    }

    public void play() {
        if(timeline == null){
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

                sec.setFont(minecraftFont);
                min.setFont(minecraftFont);
                int secValue = Integer.parseInt(this.sec.getText());
                int minValue = Integer.parseInt(this.min.getText());
                secValue++;
                if (secValue >= 60) {
                    secValue = 0;
                    minValue++;
                }

                this.sec.setText(String.format("%02d", secValue));
                this.min.setText(String.format("%02d", minValue));

                sec.setFont(minecraftFont);
                min.setFont(minecraftFont);
                System.out.println("говно");

            }));

            timeline.setCycleCount(Timeline.INDEFINITE);
        }

        timeline.play();
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }
    public void setMin(String min) {
        this.min.setText(min);
    }

    public void setSec(String sec) {
        this.sec.setText(sec);
    }
    public String getSec() {
        return this.sec.getText();
    }

    public String getMin() {
        return this.min.getText();
    }
}
