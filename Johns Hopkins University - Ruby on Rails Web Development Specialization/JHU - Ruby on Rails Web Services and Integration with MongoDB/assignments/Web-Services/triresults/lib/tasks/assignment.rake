namespace :assignment do

  desc "ingest sample data"
  task import: :environment do
    db = Mongoid.default_client.database
    db[:races].find.delete_many
    db[:racers].find.delete_many
    db[:results].find.delete_many
    #puts "races=#{db[:races].find.count}, racers=#{db[:racers].find.count}, results=#{db[:results].find.count}"
    puts "importing data..."

    File.open("./db/races.json","r").each_line do |line|
      race=JSON.parse(line)
      race["_id"]=BSON::ObjectId.from_string(race["_id"]["$oid"])
      race["date"]=Date.xmlschema(race["date"]["$date"])
      race["created_at"]=DateTime.xmlschema(race["created_at"]["$date"])
      race["updated_at"]=DateTime.xmlschema(race["updated_at"]["$date"])
      race["events"].each do |event|
        event["_id"]=BSON::ObjectId.from_string(event["_id"]["$oid"])
      end
      db[:races].insert_one(race)
    end

    File.open("./db/racers.json","r").each_line do |line|
      racer=JSON.parse(line)
      racer["_id"]=BSON::ObjectId.from_string(racer["_id"]["$oid"])
      racer["info"]["racer_id"]=BSON::ObjectId.from_string(racer["info"]["racer_id"]["$oid"])
      db[:racers].insert_one(racer)
    end

    File.open("./db/results.json","r").each_line do |line|
      entrant=JSON.parse(line)
      entrant["_id"]=BSON::ObjectId.from_string(entrant["_id"]["$oid"])
      entrant["created_at"]=DateTime.xmlschema(entrant["created_at"]["$date"])
      entrant["updated_at"]=DateTime.xmlschema(entrant["updated_at"]["$date"])
      entrant["racer"]["racer_id"]=BSON::ObjectId.from_string(entrant["racer"]["racer_id"]["$oid"])
      entrant["racer"]["_id"]=BSON::ObjectId.from_string(entrant["racer"]["_id"]["$oid"])
      entrant["race"]["_id"]=BSON::ObjectId.from_string(entrant["race"]["_id"]["$oid"])
      entrant["race"]["date"]=DateTime.xmlschema(entrant["race"]["date"]["$date"])
      if (entrant["results"] != nil) then
        entrant["results"].map do |r|
	  r["_id"] = BSON::ObjectId.from_string(r["_id"]["$oid"])
	  r["event"]["_id"] = BSON::ObjectId.from_string(r["event"]["_id"]["$oid"])
        end
      end
      db[:results].insert_one(entrant)
    end

    puts "races=#{db[:races].find.count}, racers=#{db[:racers].find.count}, results=#{db[:results].find.count}"
  end

  desc "adjust all ingested dates for assignment to current values"
  task adjust_dates: :environment do
    db = Mongoid.default_client.database
    puts "updating database: #{db.name}"

    NOW=Date.current
    max_date=db[:races].find.aggregate([ {:$group=>{:_id=>1, :max_date=>{:$max=>"$date"}}} ]).first[:max_date]
    # increment delta by one to ensure that at least one of the
    # dates falls in the future and satisfies the "upcoming" requirement
    delta_years=NOW.year - max_date.year + 1

    puts "updating race dates to current by #{delta_years} years"
    db[:races].find.each do |race|
      race_date = race[:date] + delta_years.years
      db[:races].find(:_id=>race[:_id]).update_one(:$set=>{:date=>race_date})
      db[:results].find(:"race._id"=>race[:_id]).update_many(:$set=>{:"race.date"=>race_date})
    end

    puts "updating birth years to current by #{delta_years} years"
    db[:racers].find.update_many(:$inc=>{:"race.birth_years"=>delta_years})

    puts "updating creation and update times to #{NOW}"
    db[:races].find.update_many(:$set=>{:created_at=>NOW, :updated_at=>NOW})
    db[:results].find.update_many(:$set=>{:created_at=>NOW, :updated_at=>NOW})
  end

  desc "complete setup or cleanup/refresh"
  task setup_data: :environment do
    Rake::Task["assignment:import"].invoke
    Rake::Task["assignment:adjust_dates"].invoke
  end

end
