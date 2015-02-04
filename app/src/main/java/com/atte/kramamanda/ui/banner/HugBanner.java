/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.ui.banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atte.kramamanda.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Banner to show message in.
 */
public class HugBanner extends LinearLayout {
    private static final String DAY_FORMAT = "MMMM d yyyy";

    // These are values that will be multiplied to the main color to create the different shadings
    private static final float COLOR_SHADOW_FACTOR = 0.3f;
    private static final float COLOR_EDGE_FACTOR = 0.7f;

    private Paint mPaint;
    private int mMainColor;
    private int mEdgeColor;
    private int mShadowColor;
    private Path mEdgePart;
    private Path mShadowPart;
    private RectF mMainPart;

    /**
     * Constructor.
     */
    public HugBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_hug_banner, this, true);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setMinimumHeight((int) getResources().getDimension(R.dimen.banner_height));
        setPadding(0, 0, 0, (int) getResources().getDimension(R.dimen.banner_padding_bottom));
        setGravity(Gravity.CENTER_HORIZONTAL);
        setOrientation(VERTICAL);
        setWillNotDraw(false);
        initDrawingValues();
    }

    /**
     * Sets the banner to show the given message.
     */
    public void setMessage(String message) {
        ((TextView)findViewById(R.id.banner_message)).setText(message);
    }

    /**
     * Sets the banner to show the given message.
     */
    public void setDate(long dateInMillis) {
        Date date = new Date(dateInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DAY_FORMAT);
        ((TextView) findViewById(R.id.banner_date)).setText(dateFormat.format(date));
    }

    /**
     * Called when the size of this view has changed.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createBannerShapes();
    }

    /**
     * Called to draw this view.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // Draw left edge
        mPaint.setColor(mEdgeColor);
        canvas.drawPath(mEdgePart, mPaint);

        // Draw left shadow part
        mPaint.setColor(mShadowColor);
        canvas.drawPath(mShadowPart, mPaint);

        // Mirror edge and shadow parts
        Matrix transformation = new Matrix();
        transformation.setScale(-1.0f, 1.0f);
        Path rightEdge = new Path(mEdgePart);
        rightEdge.transform(transformation);
        Path rightShadow = new Path(mShadowPart);
        rightShadow.transform(transformation);
        transformation = new Matrix();
        transformation.setTranslate(getWidth(), 0.0f);
        rightEdge.transform(transformation);
        rightShadow.transform(transformation);

        // Draw right edge
        mPaint.setColor(mEdgeColor);
        canvas.drawPath(rightEdge, mPaint);

        // Draw right shadow part
        mPaint.setColor(mShadowColor);
        canvas.drawPath(rightShadow, mPaint);

        // Draw main part
        mPaint.setColor(mMainColor);
        canvas.drawRect(mMainPart, mPaint);
    }

    /**
     * Initializes the resources needed for drawing.
     */
    private void initDrawingValues() {
        mMainColor = getResources().getColor(R.color.banner_color);
        mEdgeColor = Color.rgb(
                (int)(Color.red(mMainColor) * COLOR_EDGE_FACTOR),
                (int)(Color.green(mMainColor) * COLOR_EDGE_FACTOR),
                (int)(Color.blue(mMainColor) * COLOR_EDGE_FACTOR));
        mShadowColor = Color.rgb(
                (int)(Color.red(mMainColor) * COLOR_SHADOW_FACTOR),
                (int)(Color.green(mMainColor) * COLOR_SHADOW_FACTOR),
                (int)(Color.blue(mMainColor) * COLOR_SHADOW_FACTOR));

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * Creates the shapes used to build the banner.
     */
    private void createBannerShapes() {
        float edgeWidth = getResources().getDimension(R.dimen.dialog_hug_image_padding_horizontal);
        float mainMarginBottom = getResources().getDimension(R.dimen.banner_padding_bottom);

        // The main part
        float mainLeft = edgeWidth * 0.8f;
        float mainBottom = getHeight() - mainMarginBottom;
        mMainPart = new RectF(mainLeft, 0.0f, getWidth() - mainLeft, mainBottom);

        // The edge shape
        mEdgePart = new Path();
        mEdgePart.moveTo(0.0f, mainMarginBottom);
        mEdgePart.lineTo(edgeWidth * 0.5f, getHeight() * 0.6f);
        mEdgePart.lineTo(0.0f, getHeight());
        mEdgePart.lineTo(edgeWidth, getHeight());
        mEdgePart.lineTo(edgeWidth, mainMarginBottom);
        mEdgePart.lineTo(0.0f, mainMarginBottom);

        // Shadow part
        mShadowPart = new Path();
        mShadowPart.moveTo(mainLeft, mainBottom);
        mShadowPart.lineTo(edgeWidth, getHeight());
        mShadowPart.lineTo(edgeWidth, mainBottom);
        mShadowPart.lineTo(mainLeft, mainBottom);
    }
}