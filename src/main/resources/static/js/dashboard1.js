

// Dashboard 1 Morris-chart

Morris.Area({
        element: 'morris-area-chart',
        data: [{
            period: '2010',
            iphone: 0,
            ipad: 0,
            itouch: 0
        }, {
            period: '2011',
            iphone: 75,
            ipad: 65,
            itouch: 30
        }, {
            period: '2012',
            iphone: 32,
            ipad: 22,
            itouch: 12
        }, {
            period: '2013',
            iphone: 75,
            ipad: 65,
            itouch: 30
        }, {
            period: '2014',
            iphone: 32,
            ipad: 22,
            itouch: 12
        }, {
            period: '2015',
            iphone: 75,
            ipad: 65,
            itouch: 30
        },
         {
            period: '2016',
            iphone: 0,
            ipad: 0,
            itouch: 0
        }],
        xkey: 'period',
        ykeys: ['iphone', 'ipad', 'itouch'],
        labels: ['iPhone', 'iPad', 'iPod Touch'],
        pointSize: 0,
        fillOpacity: 0.9,
        pointStrokeColors:['#e7e8ef', '#51e4ff', '#16198d'],
        behaveLikeLine: true,
        gridLineColor: '#eef0f2',
        lineWidth: 0,
        hideHover: 'auto',
        lineColors: ['#e7e8ef', '#51e4ff', '#16198d'],
        resize: true
        
    });

 $('.vcarousel').carousel({
            interval: 3000
         })