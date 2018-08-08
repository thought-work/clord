(ns server.discord-handler
  (:require [server.mvf3 :refer [rapsheet]]
            [server.mvf4 :refer [punish]]
            [server.MVF_5 :refer [add_term]]
            [server.MVF_5 :refer [remove_term]]
            [server.MVF_5 :refer [view_terms]]))

(def commands
  [
   {
    :command "ping"                                         ; This string is the command to match
    :exec    punish                                         ; The use case is the function to execute
    }
   {
    :command "hello"
    :exec    (fn [msg-obj] (.reply msg-obj "world"))
    }
   {
    :command ".rapsheet"
    :exec    rapsheet
    }
   {
    :command ".addterm"
    :exec    add_term
    }
   {
    :command ".removeterm"
    :exec    remove_term
    }
   {
    :command ".viewterms"
    :exec    view_terms
    }
   ]
  )

(defn find-command
  [str]
  (->>
    (filter #(= (:command %) (.toLowerCase (first (.split str " ")))) commands)
    first))

(defn handle-command
  "This function checks the message and when it matches a
  command it executes a function provided"
  [bot msg]
  (let [msg-content (aget msg "content")
        command (find-command msg-content)]
       (if (nil? command)
         msg
         ((:exec command) bot msg))))
