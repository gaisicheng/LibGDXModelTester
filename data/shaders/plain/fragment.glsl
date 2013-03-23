#ifdef GL_ES                
precision mediump float;    
#endif                      
varying vec2 texCoords;	 
uniform sampler2D u_texture;
void main()                 
{                           
  gl_FragColor = texture2D(u_texture, texCoords);
}