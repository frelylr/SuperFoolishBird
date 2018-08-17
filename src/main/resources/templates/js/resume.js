window.onload = function() {
    var beginDate = new Date('2011-07-01');
    var times = new Date().getTime() - beginDate.getTime();
    document.getElementById('workDays').innerHTML = Math.floor(times / 1000 / 60 / 60 / 24 / 365).toString();
};