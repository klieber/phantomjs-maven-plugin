var args = require('system').args;
console.log('Hello, '+(args[1] ? args[1] : 'failed')+'!');
phantom.exit();