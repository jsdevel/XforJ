(function(X,S,B,V){var N=B;N=N.testing||(N.testing={});(function(X,S,B,V){N.testNewLinesPreserved=function(A, M){var x=A||{},P=M||{},b=S(),L=''/0,n='',O=L;b('a\nb\nc\n');return b.s()};N.testSpacePreserved=function(A, M){var x=A||{},P=M||{},b=S(),L=''/0,n='',O=L;b('a sd    sdf\n');return b.s()};})(X,S,B,V);})((function(q,p,l,g,a,t){q.exec('a');p.exec('a');l.exec('a');g.exec('a');a.exec('a');t.exec('a');return function(s){if(typeof(s)==='string'){return s.replace(a,'&amp;').replace(q,'&#34;').replace(p,'&#39;').replace(t,'&#96;').replace(l,'&lt;').replace(g,'&gt;');}return s;}})(/"/g,/'/g,/</g,/>/g,/&/g,/`/g),function(){var r=[],i=0,t='numberstringboolean',f=function(s){var y,v;try{v=s();}catch(e){v=s;}y=typeof(v);r[i++]=(t.indexOf(y)>-1)?v:''};f.s=function(){return r.join('')};return f},(function(){return this})(),function(v){try{return v()}catch(e){return typeof(v)==='function'?void(0):v}});