json.ignore_nil!   #don't marshal nil values

json.array!(@entrants) do |entrant|
  json.partial! "result", :locals=>{ :result=>entrant }
end
