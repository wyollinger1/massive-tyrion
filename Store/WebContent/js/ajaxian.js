require(['dojo/dom', 'dojo/query','dojo/on','dojo/request', 
    'dojo/dom-form', 'dojo/dom-class', './js/shared.js',
    'dojo/domReady!'], 
    function(dom, query, on, request, domForm, domClass, shared){
      /**
       * Setup ajax
       */
      var trConnections = [];
      /**
       * Connect searchForm's submit to ajax request and 
       * search table creation
       */
      on(dom.byId('searchForm'), "submit", function(evt){
        // prevent the page from navigating after submit
        evt.stopPropagation();
        evt.preventDefault();
        var searchForm=dom.byId('searchForm');
        console.log(domForm.toObject(searchForm)); 
        request('/Store/search', {
          query: domForm.toObject(searchForm),
          handleAs: "json",
          timeout: 2000
        }).then(function(data){
          var table=query("#searchTable table")[0];
          var newTableData=createTable(data);
          if(newTableData && table){
            table.innerHTML=newTableData;
            conTrs(table);//Add tr click connections
          }else{
            //Error processing
          }
        });
        return false;
      });
      
      //Check if logged in on load
      request.post('/Store/login', {
    	  handleAs: 'json',
    	  timeout: 2000
    	  }).then(function(user){
    		  if(user && user[0] && user[0]['name']){
    			  alert("Hello "+ user[0]['name']);
    			  shared.userCustomize(user[0]);
    		  }else{
    			//Error processing TODO: maybe bootstraps alert?
    		}
    	  });
      
      //Sign in ajax
      var logonForm = dom.byId("logonForm");
      on(logonForm, "submit", function(evt){
        // prevent the page from navigating after submit
        evt.stopPropagation();
        evt.preventDefault();

        request.post('/Store/login', {
          data: domForm.toObject('logonForm'),
          handleAs: "json",
          timeout: 2000
        }).then(function(user){

          if(user && user[0] && user[0]['name']){
            alert("Hello "+ user[0]['name']);
            shared.userCustomize(user[0]);
          }else{
            //Error processing TODO: maybe bootstraps alert?
          }
        });
        return false;
      });
      //Sign out ajax
      on(dom.byId("logout"), "click", function(evt){
        // prevent the page from navigating after submit
        evt.stopPropagation();
        evt.preventDefault();
        
        request.del('/Store/login', {
          handleAs: 'json',
          timeout: 2000
        }).then(function(out){
          if(out && out.length>0){
            out=out[0];
            if(out['out']==true){
              shared.logout();
            }
          }else{
            alert("Error not logged out");
          }
        }, function(err){
          alert(err);
        });
      });
      
      /** Purchase Form Ajax */
      on(dom.byId("purchase"), 'click', function(evt){
        var purchTag = dom.byId("purchaseId");
        if(!purchTag || !purchTag.innerHTML){
          //Error Handling
          return;
        }
        var id=purchTag.innerHTML;
        request.post("/Store/Sales", {
          data: {'mId': id, 'numToBuy': 1},
          handleAs: "json",
          timeout: 2000
        }).then(function(isPurchased){
          if(isPurchased && isPurchased.length>0){ 
            isPurchased=isPurchased[0];
            if(!("Error" in isPurchased)){
              shared.userCustomize(isPurchased['user']);
            }else{
              /*TODO:Handle Error*/
            }
          }else{
          }
        });
      });


      //TODO: Connect up rest of Customer type buttons (purchase)
      //      also make rest of HTML generators (popView, logout)
      //      Then work on Manager stuff

      /**
       * Utility functions (HTML manipulation/creation mostly
       */

      /**
       * Creates a table from a array of objects (all of the same type
       *
       * @param lOfObjs the list of all same type object
       * @return String representation of the HTML <thead> and <tbody> (no <table>)
       */
      function createTable(lOfObjs){
        if(lOfObjs.length && lOfObjs.length>0){
          var tableHead = "<thead>\n\t<tr>";
          var tableBody = "<tbody>";
          var tr ="";
          //Setup header
          for(var i in lOfObjs[0]){
            tableHead+="\n\t\t<th>"+i+"</th>";
          }
          tableHead+="\n\t</tr>\n</thead>";

          //Setup body
          for(i=0; i<lOfObjs.length; i++){
            tr = "\n\t<tr>";
            for(j in lOfObjs[i]){
              tr+="\n\t\t<td>"+lOfObjs[i][j]+"</td>";
            }
            tr +="\n\t</tr>";
            tableBody+=tr;
          }
          return tableHead+tableBody;
        }
      }

      /**
       * Connect search trs on clicks to populate the view pane
       */
      function conTrs(table){
        //Remove past connections
        while(trConnections.length>0){
          trConnections.pop().remove();
        }

        //And make some new ones
        query('tr', table).forEach(function(node, index, nodeList){
          on(node, 'click', popView);
        });
      }

      /**
       * HTML generator for populating a view from a tr click
       */
      function popView(evt){
        // prevent the page from navigating after submit
        evt.stopPropagation();
        evt.preventDefault();
        var tr =evt.target;
        while(tr.nodeName!="TR"){
          tr=tr.parentElement;
        }
        var table=tr;
        while(table.nodeName!="TABLE"){
          table=table.parentElement;
        }
        var ths = query('th', table);
        var tds=query('td', tr);
        var view ="";
        for(var i=0; i<tds.length; i++){
          if((i%3==0 && i!=0)){
            view+="</div>";
          }
          if(i%3==0){
            view+="<div class='row-fluid span12'>"
          }
          if(ths[i].innerHTML=='id'){
            view+="<div class='label span2'>"+ths[i].innerHTML+"</div>";
            view+="<div id='purchaseId' class='span2'>"+tds[i].innerHTML+"</div><div class='span2'></div>";
          }else{
            view+="<div class='label span2'>"+ths[i].innerHTML+"</div>";
            view+="<div class='span2'>"+tds[i].innerHTML+"</div>";
          }
        }
        view+="</div>";
        dom.byId("purchaseView").innerHTML=view;

      }
    });
