/*
 * Copyright 2012 Joseph Spencer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
(function(){var currentNS;try{JSL}catch(e){JSL={}}if(!JSL.rms)JSL.rms={};if(!JSL.rms.people)JSL.rms.people={};if(!JSL.rms.people.cousins)currentNS=JSL.rms.people.cousins={};JSL.rms.people.cousins.showPeople=showPeople;function showPeople(_data, _params){var data=_data||{},params=_params||{},bld=new StringBuffer();bld.append('Hello there!       ');bld.append(data.boo);bld.append('   <h2>');bld.append(data.dan);bld.append('</h2>');return bld.toString()}JSL.rms.people.cousins.showProperties=showProperties;function showProperties(_data, _params){var data=_data||{},params=_params||{},bld=new StringBuffer();bld.append('Hello there!       ');bld.append(data.boo);
      bld.append('   <h2 some=\"asdf\">');bld.append(data.dan);bld.append('</h2>');return bld.toString()}
   function StringBuffer(){
      var v=[],i=0;
      this.append=function(s){v[i++]=s||'';};
      this.toString=function(){return v.join('');};
   }
})();
