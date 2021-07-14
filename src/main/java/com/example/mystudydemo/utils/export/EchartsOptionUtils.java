package com.example.mystudydemo.utils.export;


import com.github.abel533.echarts.Label;
import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.axis.AxisLabel;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.feature.DataView;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.feature.Mark;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Funnel;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.style.ItemStyle;
import com.github.abel533.echarts.style.itemstyle.Normal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author:CCA
 * @date:2020/9/15 10:39
 * @description:
 */
public class EchartsOptionUtils {


    /**
     * @author: CCA
     * @date: 2020/9/15 10:43
     * @param: [X轴数据, Y轴数据, 柱状图名称]
     * @return: option转换完成的字符串
     * @description:柱状图Echarts的Option格式的数据生成
     */
    public static String getBarOption(String[] citis, int[] datas, String title) {
        GsonOption option = new GsonOption();
        option.title(title);
        // 工具栏
        option.toolbox().show(true).feature(
                // 辅助线
                Tool.mark,
                // 数据视图
                Tool.dataView
        );// 保存为图片
        //显示工具提示,设置提示格式
        option.tooltip().show(false).formatter("{a} <br/>{b} : {c}");

        // 图例
        option.legend(title);

        // 图类别(柱状图)
        Bar bar = new Bar(title);
        // 轴分类
        CategoryAxis category = new CategoryAxis();
        // 轴数据类别
        category.data(citis);
        // 横坐标显示不全，设置axisLabel属性
        AxisLabel axisLabel = new AxisLabel();
        axisLabel.setRotate(38);
        axisLabel.setInterval(0);

        category.axisLabel(axisLabel);
        // 循环数据
        for (int i = 0; i < citis.length; i++) {
            int data = datas[i];
            //颜色暂时写死，后期可以设置成配置
            String color = "rgb(186,73,46)";
            // 类目对应的柱状图
            Map<String, Object> map = new HashMap<String, Object>(2);
            map.put("value", data);
            map.put("itemStyle", new ItemStyle().normal(new Normal().color(color)));
            map.put("label", new Label().show(true).position("top"));
            bar.data(map);
        }
        //暂时设置为正常柱状图，后期可以设置成配置
        Boolean isHorizontal = true;
        if (isHorizontal) {// 横轴为类别、纵轴为值
            option.xAxis(category);// x轴
            option.yAxis(new ValueAxis());// y轴
        } else {// 横轴为值、纵轴为类别
            option.xAxis(new ValueAxis());// x轴
            option.yAxis(category);// y轴
        }

        option.series(bar);
        return option.toString();
    }

    /**
     * @author: CCA
     * @date: 2020/9/15 10:43
     * @param: [X轴数据, Y轴数据, 柱状图名称]
     * @return: option转换完成的字符串
     * @description:柱状图Echarts的Option格式的数据生成
     */
    public static String getBarOption(String[] citis, double[] datas, String title) {
        GsonOption option = new GsonOption();
        option.title(title);
        // 工具栏
        option.toolbox().show(true).feature(
                // 辅助线
                Tool.mark,
                // 数据视图
                Tool.dataView
        );// 保存为图片
        //显示工具提示,设置提示格式
        option.tooltip().show(false).formatter("{a} <br/>{b} : {c}");

        // 图例
        option.legend(title);

        // 图类别(柱状图)
        Bar bar = new Bar(title);
        // 轴分类
        CategoryAxis category = new CategoryAxis();
        // 轴数据类别
        category.data(citis);

        // 横坐标显示不全，设置axisLabel属性
        AxisLabel axisLabel = new AxisLabel();
        axisLabel.setRotate(38);
        axisLabel.setInterval(0);

        category.axisLabel(axisLabel);
        // 循环数据
        for (int i = 0; i < citis.length; i++) {
            double data = datas[i];
            //颜色暂时写死，后期可以设置成配置
            String color = "rgb(186,73,46)";
            // 类目对应的柱状图
            Map<String, Object> map = new HashMap<String, Object>(2);
            map.put("value", data);
            map.put("itemStyle", new ItemStyle().normal(new Normal().color(color)));
            map.put("label", new Label().show(true).position("top"));
            bar.data(map);
        }
        //暂时设置为正常柱状图，后期可以设置成配置
        Boolean isHorizontal = true;
        if (isHorizontal) {// 横轴为类别、纵轴为值
            option.xAxis(category);// x轴
            option.yAxis(new ValueAxis());// y轴
        } else {// 横轴为值、纵轴为类别
            option.xAxis(new ValueAxis());// x轴
            option.yAxis(category);// y轴
        }

        option.series(bar);
        return option.toString();
    }

    /**
     * @author: CCA
     * @date: 2020/9/15 10:43
     * @param: [X轴数据, Y轴数据, 柱状图名称]
     * @return: option转换完成的字符串
     * @description:多类型柱状图Echarts的Option格式的数据生成
     */
    public static String getMultiBarOption(String[] times, String[] cities, double[][] datas, String title) {
        GsonOption option = new GsonOption();
        // 工具栏
        option.toolbox().show(true).feature(
                // 辅助线
                Tool.mark,
                // 数据视图
                Tool.dataView
        );// 保存为图片
        //显示工具提示,设置提示格式
        option.tooltip().show(false).formatter("{a} <br/>{b} : {c}");

        // 标题
        option.title().text(title).x("left");

        //设置类型
        Legend legend = new Legend();
        legend.data(times);
        legend.y("bottom");
        option.legend(legend);

        // 轴分类
        CategoryAxis category = new CategoryAxis();
        // 轴数据类别
        category.data(cities);

        // 横坐标显示不全，设置axisLabel属性
        AxisLabel axisLabel = new AxisLabel();
        axisLabel.setRotate(38);
        axisLabel.setInterval(0);
        category.axisLabel(axisLabel);

        Bar[] bar = new Bar[2];
        // 循环数据
        for (int i = 0; i < times.length; i++) {
            Bar b = new Bar();
            // 设置数据显示
            ItemStyle itemStyle = new ItemStyle();
            Normal normal = new Normal();
            Label label = new Label();
            label.show(true);
            normal.setLabel(label);
            b.setItemStyle(itemStyle.normal(normal));

            b.name(times[i]);

            for (int j = 0; j < cities.length; j++) {
                b.data(datas[i][j]);
            }
            bar[i] = b;
        }
        //暂时设置为正常柱状图，后期可以设置成配置
        Boolean isHorizontal = true;
        if (isHorizontal) {// 横轴为类别、纵轴为值
            option.xAxis(category);// x轴
            option.yAxis(new ValueAxis());// y轴
        } else {// 横轴为值、纵轴为类别
            option.xAxis(new ValueAxis());// x轴
            option.yAxis(category);// y轴
        }

        option.series(bar);
        return option.toString();
    }

    /**
     * @author: CCA
     * @date: 2020/9/15 11:04
     * types 折线名称
     * @param: [分组名称, X轴数据, Y轴数据, 折线图名称]
     * @return: option转换完成的字符串
     * @description:折线图Echarts的Option格式的数据生成
     */
    public static String getLineOption(String[] types, String[] citis, int[][] datas, String title) {
        GsonOption option = new GsonOption();

        option.title().text(title).x("left");// 大标题、小标题、位置

        // 提示工具
        option.tooltip().trigger(Trigger.axis);// 在轴上触发提示数据
        // 工具栏
        option.toolbox().show(true).feature(Tool.saveAsImage);// 显示保存为图片
        Legend legend = new Legend();
        legend.data(types);
        legend.x("30%");
        option.legend(legend);// 图例

        CategoryAxis category = new CategoryAxis();// 轴分类
        category.data(citis);

        // 柱状图横坐标显示不全，设置axisLabel属性
        AxisLabel axisLabel = new AxisLabel();
        axisLabel.setRotate(38);
        axisLabel.setInterval(0);

        category.axisLabel(axisLabel);
        // 起始和结束两端空白策略
        category.boundaryGap(false);

        // 循环数据
        for (int i = 0; i < types.length; i++) {
            Line line = new Line();// 三条线，三个对象
            String type = types[i];
            line.name(type).stack("总量");
            for (int j = 0; j < datas[i].length; j++)
                line.data(datas[i][j]);
            option.series(line);
        }
        //暂时设置为正常折线图，后期可以设置成配置
        Boolean isHorizontal = true;
        if (isHorizontal) {// 横轴为类别、纵轴为值
            option.xAxis(category);// x轴
            option.yAxis(new ValueAxis());// y轴
        } else {// 横轴为值、纵轴为类别
            option.xAxis(new ValueAxis());// x轴
            option.yAxis(category);// y轴
        }
        return option.toString();
    }

    /**
     * @author: CCA
     * @date: 2020/9/15 11:04
     * types 折线名称
     * @param: [分组名称, X轴数据, Y轴数据, 折线图名称]
     * @return: option转换完成的字符串
     * @description:折线图Echarts的Option格式的数据生成
     */
    public static String getLineOption(String[] types, String[] citis, double[][] datas, String title) {
        GsonOption option = new GsonOption();
        // 大标题、小标题、位置
        option.title().text(title).x("left");

        // 提示工具
        // 在轴上触发提示数据
        option.tooltip().trigger(Trigger.axis);
        // 工具栏
        // 显示保存为图片
        option.toolbox().show(true).feature(Tool.saveAsImage);
        Legend legend = new Legend();
        legend.data(types);
        legend.x("30%");
        // 图例
        option.legend(legend);
        // 轴分类
        CategoryAxis category = new CategoryAxis();
        category.data(citis);

        // 柱状图横坐标显示不全，设置axisLabel属性
        AxisLabel axisLabel = new AxisLabel();
        axisLabel.setRotate(38);
        axisLabel.setInterval(0);

        category.axisLabel(axisLabel);
        // 起始和结束两端空白策略
        category.boundaryGap(false);


        // 循环数据
        for (int i = 0; i < types.length; i++) {
            // 三条线，三个对象
            Line line = new Line();

            // 设置折线图每个折点有数据
            ItemStyle itemStyle = new ItemStyle();
            Normal normal = new Normal();
            Label label = new Label();
            label.show(true);
            normal.setLabel(label);
            line.setItemStyle(itemStyle.normal(normal));

            String type = types[i];
            line.name(type);
            // 增加stack("总量")此属性 后一个系列的值会在前一个系列的值上相加
            // line.name(type).stack("总量");
            for (int j = 0; j < datas[i].length; j++)
                line.data(datas[i][j]);
            option.series(line);
        }
        //暂时设置为正常折线图，后期可以设置成配置
        Boolean isHorizontal = true;
        // 横轴为类别、纵轴为值
        if (isHorizontal) {
            // x轴
            option.xAxis(category);
            // y轴
            option.yAxis(new ValueAxis());
        } else {
            // 横轴为值、纵轴为类别
            // x轴
            option.xAxis(new ValueAxis());
            // y轴
            option.yAxis(category);
        }
        return option.toString();
    }

    /**
     * @author: CCA
     * @date: 2020/9/15 11:08
     * @param: [分组名称, 数据, 饼图名称]
     * @return: option转换完成的字符串
     * @description:饼图Echarts的Option格式的数据生成
     */
    public static String getPieOption(String[] types, int[] datas, String title) {
        GsonOption option = new GsonOption();

        // 大标题、小标题、标题位置
        option.title().text(title).subtext("").x("center");

        // 提示工具 鼠标在每一个数据项上，触发显示提示数据
        option.tooltip().trigger(Trigger.item).formatter("{a} <br/>{b} : {c} ({d}%)");

        // 工具栏
        // 辅助线
        option.toolbox().show(true).feature(new Mark().show(true),
                // 数据视图
                new DataView().show(true).readOnly(false),
                //饼图、漏斗图切换
                new MagicType().show(true).type(new Magic[]{Magic.pie, Magic.funnel}),
                // 漏斗图设置
                new Option().series(new Funnel().x("25%").width("50%").funnelAlign(X.left).max(1548)),
                // 还原
                Tool.restore,
                // 保存为图片
                Tool.saveAsImage);
        // 图例及位置
        option.legend().orient(Orient.horizontal).x("left").data(types);
        // 拖动进行计算
        option.calculable(true);

        Pie pie = new Pie();

        // 设置饼图有数据
        ItemStyle itemStyle = new ItemStyle();
        Normal normal = new Normal();
        Label label = new Label();
        label.show(true);
        // 设置数据占比
        label.formatter("{b} : {c} ({d}%)");
        normal.setLabel(label);
        pie.setItemStyle(itemStyle.normal(normal));

        // 标题、半径、位置
        pie.name(title).radius("55%").center("50%", "60%");

        // 循环数据
        for (int i = 0; i < types.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>(2);
            map.put("value", datas[i]);
            map.put("name", types[i]);
            pie.data(map);
        }

        option.series(pie);
        return option.toString();
    }

}
