String.prototype.format = function() {
  var s = this;
  for (var i = 0; i < arguments.length; i++) {
    var reg = new RegExp("\\{" + i + "\\}", "gm");
    s = s.replace(reg, arguments[i]);
  }
  return s;
}

String.prototype.endsWith = function (suffix) {
  return (this.substr(this.length - suffix.length) === suffix);
}

String.prototype.startsWith = function(prefix) {
  return (this.substr(0, prefix.length) === prefix);
}
