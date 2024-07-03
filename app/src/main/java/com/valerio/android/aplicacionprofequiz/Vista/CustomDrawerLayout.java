package com.valerio.android.aplicacionprofequiz.Vista;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.valerio.android.aplicacionprofequiz.R;

public class CustomDrawerLayout extends DrawerLayout {
    public CustomDrawerLayout(@NonNull Context context) {
        super(context);
    }

    public CustomDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Intercepta todos los eventos táctiles para decidir si se debe cerrar el drawer o no
        if (isDrawerOpen(GravityCompat.START)) {
            if (ev.getX() > getWidthOfNavDrawer()) {
                // Si el toque está fuera del área del Navigation Drawer, permitir que se cierre.
                return super.onInterceptTouchEvent(ev);
            } else {
                // Consumir el evento si es dentro del área del Navigation Drawer para evitar que se cierre.
                return false;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    private int getWidthOfNavDrawer() {
        // Retorna el ancho del Navigation Drawer. Asegúrate de tener el ID correcto para tu NavigationView.
        View navView = findViewById(R.id.nav_view);
        return navView != null ? navView.getWidth() : 0;
    }

}