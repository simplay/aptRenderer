aptRenderer
===========

#Usage
`java aprenderer WIDTH HEIGHT SPP SCENE_TASK`

Where 
+ **WIDTH** indicates the pixel width of the rendered image.
+ **HEIGHT** indicates the pixel height of the rendered image.
+ **SPP** indicates the used samples per pixel of the rendered image.
+ **SCENE_TASK** indicates the scene a user wants to render.

Example ``java aprenderer 100 200 4 1` renders an 100 by 200 image of the CameraTest Scene using 4 samples per pixel.

#Available Scene Tasks
The number in the fist column indicates the SCENE_TASK used for rendering the scene on the second column.

| SCENE_TASK        | Scene Name           | 
| ------------- |:-------------:| 
| 0      | DummyTest |
| 1      | CameraTest      |
