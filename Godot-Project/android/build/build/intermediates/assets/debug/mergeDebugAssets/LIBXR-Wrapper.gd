class_name LIBXRWrapper extends Object

## Interface used to access the functionality provided by this plugin.

var _plugin_name = "LIBXR"
var _plugin_singleton



func _init():

	print("Attempting to innitialize LIBXR, test")
	print(Engine.get_singleton_list())
	if Engine.has_singleton(_plugin_name):
		_plugin_singleton = Engine.get_singleton(_plugin_name)
	else:
		print("Initialization error: unable to access the java logic")

## Print a 'Hello World' message to the logcat.
func Connect():
	if _plugin_singleton:
		_plugin_singleton.Connect()
	else:
		printerr("Initialization error")

