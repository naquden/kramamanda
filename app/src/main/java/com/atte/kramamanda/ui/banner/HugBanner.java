/*
 * Copyright (c) 2015 Backenhof
 */
package com.atte.kramamanda.ui.banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
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
    private static final float COLOR_FOLD_FACTOR = 0.3f;
    private static final float COLOR_EDGE_FACTOR = 0.6f;

    private Paint mPaint;
    private int mMainColor;
    private int mEdgeColor;
    private int mFoldColor;
    private Path mEdgePart;
    private Path mFoldPart;
    private RectF mMainPart;
    private Shader mShadowShader;
    private RectF mShadowPart;
    private int mImageWidth;

    /**
     * Constructor.
     */
    public HugBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_hug_banner, this, true);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setMinimumHeight((int) getResources().getDimension(R.dimen.banner_height));
        int paddingBottom = (int) getResources().getDimension(R.dimen.banner_padding_bottom);
        setPadding(0, 0, 0, paddingBottom);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setOrientation(VERTICAL);
        setWillNotDraw(false);
        initDrawingValues();
    }

    /**
     * Sets the width of the image this is wrapping. This is required.
     */
    public void init(int imageWidth) {
        mImageWidth = imageWidth;
        LayoutParams textLayoutParams = new LayoutParams(imageWidth, LayoutParams.WRAP_CONTENT);
        findViewById(R.id.banner_message).setLayoutParams(textLayoutParams);
        findViewById(R.id.banner_date).setLayoutParams(textLayoutParams);
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
        mPaint.setShader(null);

        // Draw left edge
        mPaint.setColor(mEdgeColor);
        canvas.drawPath(mEdgePart, mPaint);

        // Draw left fold part
        mPaint.setColor(mFoldColor);
        canvas.drawPath(mFoldPart, mPaint);

        // Mirror edge and fold parts
        Matrix transformation = new Matrix();
        transformation.setScale(-1.0f, 1.0f);
        Path rightEdge = new Path(mEdgePart);
        rightEdge.transform(transformation);
        Path rightFold = new Path(mFoldPart);
        rightFold.transform(transformation);
        transformation = new Matrix();
        transformation.setTranslate(getWidth(), 0.0f);
        rightEdge.transform(transformation);
        rightFold.transform(transformation);

        // Draw right edge
        mPaint.setColor(mEdgeColor);
        canvas.drawPath(rightEdge, mPaint);

        // Draw right fold part
        mPaint.setColor(mFoldColor);
        canvas.drawPath(rightFold, mPaint);

        // Draw main part
        mPaint.setColor(mMainColor);
        canvas.drawRect(mMainPart, mPaint);

        // Draw shadow below main part
        mPaint.setShader(mShadowShader);
        canvas.drawRect(mShadowPart, mPaint);
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
        mFoldColor = Color.rgb(
                (int)(Color.red(mMainColor) * COLOR_FOLD_FACTOR),
                (int)(Color.green(mMainColor) * COLOR_FOLD_FACTOR),
                (int)(Color.blue(mMainColor) * COLOR_FOLD_FACTOR));

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

        // The edge shape
        float edgeRight = (getWidth() - mImageWidth) / 2.0f;
        float edgeLeft = edgeRight - edgeWidth;
        mEdgePart = new Path();
        mEdgePart.moveTo(edgeLeft, mainMarginBottom);
        mEdgePart.lineTo(edgeLeft + edgeWidth/ 2.0f, getHeight() * 0.6f);
        mEdgePart.lineTo(edgeLeft, getHeight());
        mEdgePart.lineTo(edgeRight, getHeight());
        mEdgePart.lineTo(edgeRight, mainMarginBottom);
        mEdgePart.lineTo(edgeLeft, mainMarginBottom);

        // The main part
        float mainLeft = edgeRight - edgeWidth * 0.2f;
        float mainBottom = getHeight() - mainMarginBottom;
        mMainPart = new RectF(mainLeft, 0.0f, getWidth() - mainLeft, mainBottom);

        // Fold part
        mFoldPart = new Path();
        mFoldPart.moveTo(mainLeft, mainBottom);
        mFoldPart.lineTo(edgeRight, getHeight());
        mFoldPart.lineTo(edgeRight, mainBottom);
        mFoldPart.lineTo(mainLeft, mainBottom);

        // Shadow shader below main part
        mShadowPart = new RectF(edgeRight, mainBottom, getWidth() - edgeRight, getHeight());
        mShadowShader = new LinearGradient(
                0,
                mShadowPart.top,
                0,
                mShadowPart.bottom,
                0x99000000,
                0x00000000,
                Shader.TileMode.CLAMP);
    }
}