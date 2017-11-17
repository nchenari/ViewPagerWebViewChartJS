(function () {
'use strict';
angular.module('ChartApp', ['tc.chartjs'])
.controller('RTChartCtrl', RTChartCtrl)
.controller('MSIChartCtrl', MSIChartCtrl)
.controller('ProbChartCtrl', ProbChartCtrl);

// Doughnut type chart
RTChartCtrl.$inject = ['$scope'];
function RTChartCtrl($scope) {
  $scope.data = {
    // These labels appear in the legend and in the tooltips when hovering over different arcs
    labels : ["Vis-Aud", "Aud-Tact", "Vis-Tact", "Trisensory"],
    datasets: [{
      data: [30, 20, 40, 10],
      backgroundColor: [
        "#637b85",
        "#2c9c69",
        "#dbba34",
        "#c62f29"
      ],
      hoverBackgroundColor: [
        "#637b85",
        "#2c9c69",
        "#dbba34",
        "#c62f29"
      ]
    }]
  };

 $scope.options = {
    title: {
        display: true,
        text: 'Individual Stimuli Reaction Time Comparison'
    }
 };

}

// Bar type chart
MSIChartCtrl.$inject = ['$scope'];
function MSIChartCtrl($scope) {
  $scope.data = {
    labels : ["Vis-Aud MSI","Aud-Tact MSI ","Vis-Tact MSI","Trisensory MSI"],
    // datasets : [
    //   {
    //       fillColor : "#637b85",
    //       strokeColor : "rgba(220,220,220,1)",
    //       data : [375,433,591,644]
    //   }
    // ]
    datasets: [{
      label: "Millisecond RT",
      data: [375, 433, 591, 644],
      backgroundColor: [
        "#637b85",
        "#2c9c69",
        "#dbba34",
        "#c62f29"
      ],
      borderColor: [
        "rgba(220,220,220,1)"
      ],
      borderWidth: 1
    }]
  };

  $scope.options = {
    barDatasetSpacing: 15,
    barValueSpacing: 10,
    scales: {
         xAxes: [{
             stacked: true
         }],
         yAxes: [{
             stacked: true
         }]
     },
     title: {
         display: true,
         text: 'Bisensory and Trisensory MSI Comparisons'
     }
  };
}

// Line type chart
ProbChartCtrl.$inject = ['$scope'];
function ProbChartCtrl($scope) {
  $scope.data = {
    labels : ["Vis-Aud MSI","Aud-Tact MSI ","Vis-Tact MSI","Trisensory MSI"],
      datasets : [
        {
          label: "Summed Unisensory",
          backgroundColor : "#c62f29",
          borderColor : "rgba(220,220,220,1)",
          data : [375,433,591,644]
        },
        {
          label: "Trisensory",
          backgroundColor : "#637b85",
          borderColor : "rgba(220,220,220,1)",
          data : [523,543,325,398]
        },
      ]
  };

  $scope.options = {
    scales: {
         xAxes: [{
             stacked: true
         }],
         yAxes: [{
             stacked: true
         }]
     },
     legend: {
         display: true,
         labels: {
             fontColor: 'rgb(255, 99, 132)'
         }
     },
     title: {
         display: true,
         text: 'Summed Uniseonsory vs. Trisensory RT Probabilities'
     }
  };
}

})();
