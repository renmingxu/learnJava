package com.renmingxu.test.swing;

import java.awt.*;

/**
 * Created by renmingxu on 2017/2/10.
 */
public class CircleLayout implements LayoutManager {
    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return parent.getPreferredSize();
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return parent.getMinimumSize();
    }

    @Override
    public void layoutContainer(Container parent) {
        System.out.println(parent);
        double centerX = parent.getBounds().getCenterX();

        double centerY = parent.getBounds().getCenterY();

        Insets insets = parent.getInsets();

        double horizon = centerX - insets.left;

        double vertical = centerY - insets.top;

        double radius = horizon > vertical ? vertical : horizon;

        int count = parent.getComponentCount();

        for (int i = 0; i < count; i++) {

            Component component = parent.getComponent(i);

            System.out.println(component);
            if (component.isVisible()) {

                Dimension size = component.getPreferredSize();

                double angle = 2 * Math.PI * i / count;

                double x = centerX + radius * Math.sin(angle);

                double y = centerY - radius * Math.cos(angle);

                component.setBounds((int) x - size.width / 2,
                        (int) y - size.height / 2,
                        size.width, size.height);
            }
        }
    }
}
