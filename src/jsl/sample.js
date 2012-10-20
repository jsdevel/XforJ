/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


processTemplate({
   people:[
      {
         name:"sam",
         age:36
      },
      {
         name:"joe",
         age:28
      }
   ],title:"People in the world"
});

function processTemplate(data){
   var _output = {
      value:"",
      toString:function(){return this.value;},
      append:function(val){this.value+=val;return this;}
   };

   if(!!data && typeof data === 'object' && data['people']){
      _output.
         append("<h2>").
         append(data['title']||"").
         append("</h2><div>").
         append((function(arr){
            var len=arr.length,i=0;
            var item;
            for(;i<len;){
               item=arr[i++];
               _output.
                  append("<div><span>Name: ").
                  append(item['name']).
                  append("</span><span>").
                  append(item['age']).
                  append("</span></div>");
            }
         })(data['people']||[])).
         append("</div>");
   } else {
      _output.append("<span>Sorry</span>");
   }
   return _output.toString();
}
(function(){var currentNS;if(!window.JSL)JSL={};if(!JSL.rms)JSL.rms={};if(!JSL.rms.people)JSL.rms.people={};if(!JSL.rms.people.cousins)currentNS=JSL.rms.people.cousins={};rms.people.cousins.showPeople=showPeople;function showPeople(_data, _params){var data=_data||{},params=_params||{};return "Hello there! "+data.boo+""}})();