var page = require('webpage').create();
page.open('index.html', function (status) {
    phantom.exit();
});