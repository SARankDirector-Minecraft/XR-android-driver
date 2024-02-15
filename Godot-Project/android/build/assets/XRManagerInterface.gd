extends Node

var Wrapper



# Called when the node enters the scene tree for the first time.
func _ready():
	print("XRMI started")
	Wrapper = LIBXRWrapper.new()
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass
