require(['dojo/dom', 'dojo/query','dojo/on','dojo/request', 
    'dojo/dom-form', 'dojo/dom-class', 'dojo/domReady!'], 
    function(dom, query, on, request, domForm, domClass){
      /**
       * Connect form events to functions
       */
      on(dom.byId("addNewMediaForm"), 'submit', submitForm);
      on(dom.byId("addMediaForm"), 'submit', submitForm);
      on(dom.byId("delMediaForm"), 'submit', submitForm);
      on(dom.byId("itemSalesForm"), 'submit', submitForm);
      on(dom.byId("custInfoForm"), 'submit', submitForm);
      on(dom.byId("ttlSalesBut"), 'click', getTtlSales);
      on(dom.byId("aTotalSales"), 'click', getTtlSales);

      /**
       * Ajax Form ID to URL and Method mapping
       */
      var idToAjax = {
        'addNewMediaForm': {'URL': '/Store/media', 'method':'post',
        'func': function(media){
          if(media && media.length>0){
            media=media[0];
            if("Error" in media){//Known error
              alert(media["Error"]); 
            }else{ //Success
              alert("Media #: "+media['id']+ " Name: "+
                  media['name']);
            }
          }else{ //Unknown Error
            alert("Malformed response");
          }
        }},

        'addMediaForm': {'URL': '/Store/media', 'method':'post', 
        'func': function(media){
          if(media && media.length>0){
            media=media[0];
            if("Error" in media){//Known error
              alert(media["Error"]); 
            }else{ //Success
              alert("Media #: "+media['id']+ " Name: "+
                  media['name']);
            }
          }else{ //Unknown Error
            alert("Malformed response");
          }
        }},

        'delMediaForm': {'URL': '/Store/media', 'method':'delete',
        'func': function(media){
          if(media && media.length>0){
            media=media[0];
            if("Error" in media){//Known error
              alert(media["Error"]); 
            }else{ //Success
              alert("Media #: "+media['id']+ " Name: "+
                  media['name']);
            }
          }else{ //Unknown Error
            alert("Malformed response");
          }
        }},
        
        "itemSalesForm": {'URL': '/Store/Sales', 'method':'get', 
        'func':function(sales){
          if(sales && sales.length>0){
            sales=sales[0];
            if("Error" in sales){//Known error
              alert(sales["Error"]); 
            }else{ //Success
            var media=sales['media'];
            dom.byId("dispItemSales").innerHTML="Media #: "+media['id']
              + ";\tName: "+ media['name']+ ";\tTotal Sales: " + 
              sales['numSold'];
            }
          }else{ //Unknown Error
            alert("Malformed response");
          }
        }},
        
        'custInfoForm' : {'URL': '/Store/User', 'method':'get', 
        'func':function(user){
          if(user && user.length>0){
            user=user[0];
            if("Error" in user){//Known error
              alert(user["Error"]); 
            }else{ //Success
              alert("User #: "+user['id']+ " Name: "+
                  user['name']);
            }
          }else{ //Unknown Error
            alert("Malformed response");
          }
        }}
      };

      function submitForm(evt){
        // prevent the page from navigating after submit
        evt.stopPropagation();
        evt.preventDefault();
        var form = evt.target;
        while(form.nodeName!="FORM"){
        	form=form.parentElement;
        }
        var id = form.name;
        
        if(!(id in idToAjax)){
          console.log("What's this then:", id);
          return;
        }
        var myAjax=idToAjax[id];
        if(myAjax['method']=='get'){
          request(myAjax['URL'], {
            query: domForm.toObject(evt.target),
            handleAs: "json",
            timeout: 2000
          }).then(myAjax['func']);
        }else if(myAjax['method']=='post'){
          request.post(myAjax['URL'], {
            data: domForm.toObject(evt.target),
            handleAs: "json",
            timeout: 2000
          }).then(myAjax['func']);
        }else if(myAjax['method']=='delete'){
          request.del(myAjax['URL'], {
            query: domForm.toObject(evt.target),
            handleAs: "json",
            timeout: 2000
          }).then(myAjax['func'], function(err){
        	  console.log(err);
          });
        }
      }
      
      function getTtlSales(evt){
        request('/Store/Sales', {
          handleAs: 'json',
          timeout: 2000
        }).then(function(sales){
          if(sales && sales.length>0){
            sales=sales[0];
            if("Error" in sales){//Known error
              alert(sales["Error"]);
            }else{ //Success
              dom.byId("dispTtlSales").innerHTML=sales['totalSales'];
            }
          }else{//Unknown Error
            alert("Malformed response");
          }
        });
      }
    });
