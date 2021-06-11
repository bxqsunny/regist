<template>
  <div class="app-container">
    <div id="main" style="width: 800px; height: 500px;margin：0 auto"></div>
  </div>
</template>

<script>
import { detailInfo, emigrationEchart } from "@/api/level";
import * as echarts from "echarts";
export default {
  data() {
    return {
      detailInfolist: [],
    };
  },
  methods: {
    detailEchart() {
      detailInfo().then((res) => {
        this.detailInfolist = res.data;
      });
    },
  },
  mounted() {
    let xdata = [];
    let ydata = [];
    let emgrationData = [];
    emigrationEchart().then((emires) => {
      // 遍历迁出统计
      emires.data.forEach((item) => {
        emgrationData.push(item.num);
      });
      // console.log(emgrationData);
      detailInfo().then((res) => {
        this.detailInfolist = res.data;
        res.data.forEach((item) => {
          xdata.push(new Date(item.optionData).toLocaleDateString());
          ydata.push(item.num);
        });
        var chartDom = document.getElementById("main");
        var myChart = echarts.init(chartDom);
        var option;
        option = {
          title: {
            text: "社区人员统计",
          },
          tooltip: {
            trigger: "axis",
          },
          legend: {
            data: ["迁入统计", "迁出统计"],
          },
          toolbox: {
            show: true,
            feature: {
              dataZoom: {
                yAxisIndex: "none",
              },
              dataView: { readOnly: false },
              magicType: { type: ["line", "bar"] },
              restore: {},
              saveAsImage: {},
            },
          },
          xAxis: {
            type: "category",
            boundaryGap: false,
            data: xdata,
          },
          yAxis: { type: "value" },
          series: [
            {
              name: "迁入统计",
              type: "line",
              data: ydata,
            },
            {
              name: "迁出统计",
              type: "line",
              data: emgrationData ,
            },
          ],
        };
        option && myChart.setOption(option);
      });
    });
  }
};
</script>