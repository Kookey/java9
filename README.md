# java9
java9新特性相关的代码Demo

## Request

    @Override
    public AsyncContext startAsync() {
        return startAsync(getRequest(),response.getResponse());
    }
    
    @Override
    public AsyncContext startAsync(ServletRequest request,
            ServletResponse response) {
        if (!isAsyncSupported()) {
            IllegalStateException ise =
                    new IllegalStateException(sm.getString("request.asyncNotSupported"));
            log.warn(sm.getString("coyoteRequest.noAsync",
                    StringUtils.join(getNonAsyncClassNames())), ise);
            throw ise;
        }
    
        if (asyncContext == null) {
            asyncContext = new AsyncContextImpl(this);
        }
    
        asyncContext.setStarted(getContext(), request, response,
                request==getRequest() && response==getResponse().getResponse());
        asyncContext.setTimeout(getConnector().getAsyncTimeout());
    
        return asyncContext;
    }


## AsyncContext

     public void setStarted(Context context, ServletRequest request,
             ServletResponse response, boolean originalRequestResponse) {
    
         synchronized (asyncContextLock) {
             this.request.getCoyoteRequest().action(
                     ActionCode.ASYNC_START, this);
    
             this.context = context;
             this.servletRequest = request;
             this.servletResponse = response;
             this.hasOriginalRequestAndResponse = originalRequestResponse;
             this.event = new AsyncEvent(this, request, response);
    
             List<AsyncListenerWrapper> listenersCopy = new ArrayList<>();
             listenersCopy.addAll(listeners);
             listeners.clear();
             for (AsyncListenerWrapper listener : listenersCopy) {
                 try {
                     listener.fireOnStartAsync(event);
                 } catch (Throwable t) {
                     ExceptionUtils.handleThrowable(t);
                     log.warn("onStartAsync() failed for listener of type [" +
                             listener.getClass().getName() + "]", t);
                 }
             }
         }
     }

当调用Request>startAsync()方法时,AsyncContext会被调用,这个时候就会调用listener.fireOnStartAsync(event);但是这个时候还没有注册Listener.所以不会触发listener.fireOnStartAsync事件.